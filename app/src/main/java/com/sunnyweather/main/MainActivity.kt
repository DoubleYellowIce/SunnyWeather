package com.sunnyweather.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.github.gzuliyujiang.dialog.DialogConfig
import com.github.gzuliyujiang.dialog.DialogStyle
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import com.github.gzuliyujiang.wheelpicker.utility.AddressJsonParser
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.sunnyweather.R
import com.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.base.BaseActivity
import com.sunnyweather.databinding.ActivityMainBinding
import data.Status.*
import data.weather.model.CombineWeatherInfo
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var viewModel: MainViewModel
    private var picker: AddressPicker? = null
    private var locationClient: LocationClient? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setUpBinding()
        viewModel.retrieveProvinceAndCityFromSP()
        transparentStatusBar()
        setUpListeners()
        if (permissionForLocationIsGranted()) {
            startLocateUser()
        } else {
            val requestCallback = RequestCallback { permissionForLocationIsGranted, _, _ ->
                if (permissionForLocationIsGranted) {
                    startLocateUser()
                } else {
                    doNothing()
                }
            }
            requestLocationPermission(requestCallback)
        }
        getCurrentLocationCombineWeatherInfo()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel
    }

    private fun transparentStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    private fun setUpListeners() {
        binding.apply {
            currentLocation.setOnClickListener {
                if (picker == null) {
                    initPicker(
                        provinceName = viewModel.getCurrentProvinceValue(),
                        cityName = viewModel.getCurrentCityValue()
                    )
                }
                picker!!.show()
            }
            swipeRefreshLayout.setOnRefreshListener() {
                getCurrentLocationCombineWeatherInfo()
            }
        }
    }

    private fun initPicker(provinceName: String, cityName: String) {
        DialogConfig.setDialogStyle(DialogStyle.Two)
        picker = AddressPicker(this).apply {
            setAddressMode(
                "city.json", AddressMode.PROVINCE_CITY,
                AddressJsonParser.Builder()
                    .provinceCodeField("code")
                    .provinceNameField("name")
                    .provinceChildField("city")
                    .cityCodeField("code")
                    .cityNameField("name")
                    .cityChildField("area")
                    .countyCodeField("code")
                    .countyNameField("name")
                    .build()
            )
            setOnAddressPickedListener { province, city, _ ->
                viewModel.updateProvinceAndCity(province!!.name, city!!.name)
                getCurrentLocationCombineWeatherInfo()
            }
            setDefaultValue(provinceName, cityName, "")
        }
    }

    private fun permissionForLocationIsGranted(): Boolean {
        return PermissionX.isGranted(this, ACCESS_FINE_LOCATION)
    }

    private fun requestLocationPermission(requestCallback: RequestCallback) {
        PermissionX.init(this)
            .permissions(
                ACCESS_FINE_LOCATION
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList, "获取GPS定位权限可以更方便获取您所在地的天气信息，为您提供更好的服务。",
                    "欣然接受", "残忍拒绝"
                )
            }
            .request(requestCallback)
    }

    private fun initLocationClient() {
        locationClient = LocationClient(applicationContext).apply {
            registerLocationListener(object : BDAbstractLocationListener() {
                override fun onReceiveLocation(location: BDLocation?) {
                    val locatedCity = location?.city!!
                    val locatedProvince = location.province!!
                    if (locatedCityIsNotSameAsCurrentCity(locatedCity)) {
                        val positiveCallback = OnClickListener { _, _ ->
                            viewModel.updateProvinceAndCity(locatedProvince, locatedCity)
                            getCurrentLocationCombineWeatherInfo()
                        }
                        askUserWillingToChangeCurrentCity(locatedCity, positiveCallback)
                    }
                    stopLocateUser()
                }
            })
            locOption = LocationClientOption().apply {
                scanSpan = 1000
                openGps = true
                setIsNeedAddress(true)
                setCoorType("WGS84")
            }
        }
    }

    private fun locatedCityIsNotSameAsCurrentCity(locatedCity: String): Boolean {
        return locatedCity != viewModel.getCurrentCityValue()
    }

    private fun askUserWillingToChangeCurrentCity(
        locatedCity: String,
        positiveCallback: OnClickListener
    ) {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("温馨提示")
            setMessage("定位显示您在$locatedCity，是否需要显示该城市的天气信息")
            setPositiveButton("是", positiveCallback)
            setNegativeButton("否") { _, _ ->
                doNothing()
            }
        }.show()
    }

    private fun stopLocateUser() {
        locationClient!!.stop()
    }

    private fun startLocateUser() {
        if (locationClient == null) {
            initLocationClient()
        }
        locationClient!!.start()
    }

    private fun getCurrentLocationCombineWeatherInfo() {
        viewModel.getCurrentLocationCombineWeatherInfo().observe(this) {
            it?.let { response ->
                when (response.status) {
                    SUCCESS -> {
                        Log.d(SunnyWeatherApplication.TestToken, "Successfully get weather info")
                        refreshDataBinding(combineWeatherInfo = response.data!!)
                        stopSwipeFreshLayoutFreshening()
                    }
                    LOADING -> {
                        startSwipeFreshLayoutFreshening()
                    }
                    ERROR -> {
                        stopSwipeFreshLayoutFreshening()
                        showErrorToast()
                        Log.d(SunnyWeatherApplication.TestToken, response.message!!)

                    }
                }
            }
        }
    }

    private fun refreshDataBinding(combineWeatherInfo: CombineWeatherInfo) {
        binding.currentDetailedWeather =
            combineWeatherInfo.currentWeather.weather[0].detailedWeather
        binding.weatherSuggestion = combineWeatherInfo.weatherSuggestion.results[0].suggestion
        binding.weatherForecastResult = combineWeatherInfo.weatherForecast.results[0]
    }

    private fun startSwipeFreshLayoutFreshening() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun stopSwipeFreshLayoutFreshening() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showErrorToast() {
        Toast.makeText(this, "获取天气数据失败，请稍后重试", Toast.LENGTH_LONG).show()
    }

    private fun doNothing() {
    }
}