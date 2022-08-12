package com.sunnyweather.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.DialogInterface.OnClickListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.github.gzuliyujiang.wheelpicker.contract.OnAddressPickedListener
import com.github.gzuliyujiang.wheelpicker.entity.CityEntity
import com.github.gzuliyujiang.wheelpicker.entity.CountyEntity
import com.github.gzuliyujiang.wheelpicker.entity.ProvinceEntity
import com.github.gzuliyujiang.wheelpicker.utility.AddressJsonParser
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.sunnyweather.R
import com.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.base.BaseActivity
import com.sunnyweather.databinding.ActivityMainBinding
import data.Status.*
import data.weather.model.CombineWeatherInfo
import utils.LogUtil
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View, View.OnClickListener,
    OnAddressPickedListener {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var picker: AddressPicker
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationClient: LocationClient


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        setUpBinding()
        viewModel.retrieveProvinceAndCityFromSP()
        transparentStatusBar()
        setUpListeners()
        initPicker(
            provinceName = viewModel.getCurrentProvinceValue(),
            cityName = viewModel.getCurrentCityValue()
        )
        initLocationClient()
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
        binding.currentLocation.setOnClickListener(this)
        binding.swipeRefreshLayout.setOnRefreshListener() {
            getCurrentLocationCombineWeatherInfo()
        }

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
                        askUserWillingToChangeCurrentCity(locatedProvince, positiveCallback)
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

    private fun initPicker(provinceName: String, cityName: String) {
        DialogConfig.setDialogStyle(DialogStyle.Two)
        picker = AddressPicker(this).apply {
            setAddressMode(
                "city.json", AddressMode.PROVINCE_CITY,
                AddressJsonParser.Builder()
                    .provinceCodeField("weatherCode")
                    .provinceNameField("name")
                    .provinceChildField("city")
                    .cityCodeField("weatherCode")
                    .cityNameField("name")
                    .cityChildField("area")
                    .countyCodeField("weatherCode")
                    .countyNameField("name")
                    .build()
            )
            setOnAddressPickedListener(this@MainActivity)
            setDefaultValue(provinceName, cityName, "")
        }
    }

    private fun startLocateUser() {
        locationClient.start()
    }

    private fun stopLocateUser() {
        locationClient.stop()
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

    private fun doNothing() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAddressPicked(
        province: ProvinceEntity?, city: CityEntity?, county: CountyEntity?
    ) {
        LogUtil.v(msg = "the address is $province $city $county")
        viewModel.updateProvinceAndCity(province!!.name, city!!.name)
        getCurrentLocationCombineWeatherInfo()
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

    private fun showErrorToast() {
        Toast.makeText(this, "获取天气数据失败，请稍后重试", Toast.LENGTH_LONG).show()
    }


    private fun startSwipeFreshLayoutFreshening() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun stopSwipeFreshLayoutFreshening() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun refreshDataBinding(combineWeatherInfo: CombineWeatherInfo) {
        binding.currentDetailedWeather =
            combineWeatherInfo.currentWeather.weather[0].detailedWeather
        binding.weatherSuggestion = combineWeatherInfo.weatherSuggestion.results[0].suggestion
        binding.weatherForecastResult = combineWeatherInfo.weatherForecast.results[0]
    }

    override fun onClick(v: View?) {
        picker.show()
    }

}