package com.example.sunnyweather

import android.Manifest.permission.*
import android.content.Context
import android.content.SharedPreferences
import android.os.*
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.example.sunnyweather.databinding.ActivityMainBinding
import com.example.sunnyweather.ui.nowData.NowDataViewModel
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


class MainActivity : AppCompatActivity(), View.OnClickListener,OnAddressPickedListener {

    companion object{
        lateinit var refreshDataHandler:Handler
    }

    private lateinit var nowViewModel: NowDataViewModel

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var forecastLayout:LinearLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var locationTextView: TextView

    private lateinit var picker: AddressPicker

    //store the city the user had chosen the last time
    private lateinit var locationRegister:SharedPreferences

    private lateinit var mLocationClient: LocationClient

    private lateinit var mLocationClientOption:LocationClientOption

    private lateinit var editor:SharedPreferences.Editor

    //the city whose weather information is showing
    private lateinit var currentCity:String

    private lateinit var mLocationListener:BDAbstractLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
        observeData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeData(){
        nowViewModel.nowData.observe(this){
            if (it!=null){
                dataBinding.apply {
                    temperature=it.now.temperature
                    weather=it.now.text
                    code=it.now.code
                    location=it.location.name
                }

            }
        }

        nowViewModel.dailyData.observe(this){
            LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel.dailyData.observe")
            if (it!=null){
                dataBinding.dailyResponse=it
            }
        }

        nowViewModel.suggestionData.observe(this){
            LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel.suggestionData.observe")
            if (it!=null){
                dataBinding.apply {
                    carWashing=it.suggestion.car_washing.brief
                    uv=it.suggestion.uv.brief
                    flu=it.suggestion.flu.brief
                    dressing=it.suggestion.dressing.brief
                }

            }
        }

        swipeRefreshLayout.setOnRefreshListener() {
            LogUtil.d(SunnyWeatherApplication.TestToken,"swipeRefreshLayout.setOnRefreshListener")
            nowViewModel.refreshData()
        }
    }

    //when the provinceName is one of "北京市","上海市","天津市","重庆市"
    //the provinceName is actually a cityName
    //we should treat it as cityName to get the weather information from the internet
    private fun isProvince(provinceName:String):Boolean{
        return when(provinceName){
            "北京市","上海市","天津市","重庆市"->{
                false
            }
            else -> true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initial(){

        nowViewModel=ViewModelProvider(this).get(NowDataViewModel::class.java)
        lifecycle.addObserver(nowViewModel)

        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.nowViewModel=nowViewModel

        forecastLayout=findViewById(R.id.forecastLayout)
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout)

        //when this textview is clicked,the picked will show up
        locationTextView=findViewById(R.id.placeName)
        locationTextView.setOnClickListener(this)

        //set statusBar to transparent
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        //after the app refreshing the weather information
        //the refreshDataHandler will receive a message
        refreshDataHandler=object :Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                LogUtil.d(SunnyWeatherApplication.TestToken,"handleMessage")
                when(msg.obj){
                    "refreshDataSuccessfully" -> Toast.makeText(this@MainActivity,"刷新成功",Toast.LENGTH_SHORT).show()
                    "failToRefreshData" ->Toast.makeText(this@MainActivity,"刷新失败",Toast.LENGTH_SHORT).show()
                    "networkIsNotWorking"->Toast.makeText(this@MainActivity,"网络不给力，请检查WIFI或者蜂窝网络是否已开启。",Toast.LENGTH_SHORT).show()
                }
                swipeRefreshLayout.isRefreshing=false
            }
        }

        //retrieve the last location which the users has chosen from SharedPreferences
        //the default value is Beijing City
        locationRegister=getSharedPreferences("locationRegister",Context.MODE_PRIVATE)
        val provinceName=locationRegister.getString("provinceName","北京市")
        val cityName=locationRegister.getString("cityName","北京市")
        currentCity= cityName!!
        nowViewModel.location.value=if (isProvince(provinceName!!)) cityName else provinceName


        mLocationListener=object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                LogUtil.d(SunnyWeatherApplication.TestToken,"onReceiveLocation")
                val locatedCity=location?.city
                val locatedProvince=location?.province
                LogUtil.d(SunnyWeatherApplication.TestToken,"the locatedProvince is $locatedProvince")
                LogUtil.d(SunnyWeatherApplication.TestToken,"the locatedCity is $locatedCity")
                LogUtil.d(SunnyWeatherApplication.TestToken, "the currentCity is $currentCity")

                //the default setting is to locate the user's position only once
                //when both the locatedCity and locatedProvince are not null
                //then it means that the app locate successfully,
                //then the app will stop locating
                //if the locatedCity is not the same as the city whose weather information is currently showing
                //ask the user if him/her would like to see the locatedCity's weather information
                //if the locatedCity is the same as the city,the app will not do anything
                    if (locatedProvince!=null&&locatedCity!=null){

                    if (locatedCity != currentCity){
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("温馨提示")
                            setMessage("定位显示您在$locatedCity，是否需要显示该城市的天气信息")
                            setPositiveButton("是"){
                                    dialog, which->
                                currentCity=locatedCity
                                nowViewModel.location.value=locatedCity
                                nowViewModel.refreshData()
                                writeLocationToEditor(locatedProvince,locatedCity)
                            }
                            setNegativeButton("否"){
                                    dialog, which->
                                //do nothing
                            }
                        }.show()
                    }
                    mLocationClient.stop()
                }
            }
        }


        //WGS84 is the international coordination type
        mLocationClientOption= LocationClientOption().apply {
            scanSpan=1000
            openGps=true
            setIsNeedAddress(true)
            setCoorType("WGS84")
        }
        mLocationClient= LocationClient(applicationContext).apply {
            registerLocationListener(mLocationListener)
            locOption=mLocationClientOption
            start()
        }

        //the default setting of the picker
        DialogConfig.setDialogStyle(DialogStyle.Two)
        picker =  AddressPicker(this).apply {
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
                    .build())
            setDefaultValue(provinceName, cityName, "")
            setOnAddressPickedListener(this@MainActivity)
        }

        PermissionX.init(this)
            .permissions( ACCESS_FINE_LOCATION
                )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "获取GPS定位权限可以更方便获取您所在地的天气信息，为您提供更好的服务。",
                    "欣然接受", "残忍拒绝")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAddressPicked(province: ProvinceEntity?, city: CityEntity?, county: CountyEntity?
    ) {
        LogUtil.v(SunnyWeatherApplication.TestToken,"the address is "+province+city+county)
        val provinceName=province!!.name
        val cityName=city!!.name
        currentCity=cityName
        nowViewModel.location.value= if (isProvince(provinceName!!)) cityName else provinceName
        nowViewModel.refreshData()
        writeLocationToEditor(provinceName,cityName)

    }

    private fun writeLocationToEditor(provinceName: String,cityName:String){
        editor=locationRegister.edit().apply {
            putString("provinceName",provinceName)
            putString("cityName",cityName)
            apply()
        }

    }

    override fun onClick(v: View?) {
        picker.show()
    }

}