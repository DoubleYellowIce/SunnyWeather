package com.example.sunnyweather

import android.Manifest.permission.*
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
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

    private lateinit var nowViewModel: NowDataViewModel
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var forecastLayout:LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var locationTextView: TextView
    private lateinit var picker: AddressPicker
    private lateinit var locationRegister:SharedPreferences
    private lateinit var mLocationClient:AMapLocationClient
    private lateinit var mLocationListener:AMapLocationListener
    private lateinit var mLocationOption:AMapLocationClientOption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
        observeData()
    }

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
            swipeRefreshLayout.isRefreshing=false
            Toast.makeText(this,"刷新成功",Toast.LENGTH_SHORT).show();
        }
    }

    private fun isProvince(provinceName:String):Boolean{
        return when(provinceName){
            "北京市","上海市","天津市","重庆市"->{
                false
            }
            else -> true
        }
    }

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


        locationRegister=getSharedPreferences("locationRegister",Context.MODE_PRIVATE)
        val provinceName=locationRegister.getString("provinceName","北京市")
        val cityName=locationRegister.getString("cityName","北京市")
        nowViewModel.location.value=if (isProvince(provinceName!!)) cityName else provinceName


        //the default setting of the picker
        DialogConfig.setDialogStyle(DialogStyle.Two);
        picker =  AddressPicker(this);
        picker.setAddressMode(
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
        picker.setDefaultValue(provinceName, cityName, "")
        picker.setOnAddressPickedListener(this)



        mLocationListener= AMapLocationListener {
            if (it != null) {
                if (it.errorCode==0){
                    LogUtil.d(SunnyWeatherApplication.TestToken,"AMapLocationListener work successfully,the city is "+it.city)

                }else{
                    LogUtil.d(SunnyWeatherApplication.TestToken,"AMapLocationListener's errorCode is "+it.errorCode.toString()+" and error info is "+it.errorInfo)

                }
            }else {
                LogUtil.d(SunnyWeatherApplication.TestToken,"There is something wrong in mLocationListener")

            }
        }
        mLocationOption=AMapLocationClientOption()
        mLocationClient=AMapLocationClient(applicationContext).apply {
            setLocationListener(mLocationListener)
            setLocationOption(mLocationOption)
        }
        mLocationClient.startLocation()


        PermissionX.init(this)
            .permissions( ACCESS_FINE_LOCATION,
                ACCESS_LOCATION_EXTRA_COMMANDS
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

    override fun onAddressPicked(province: ProvinceEntity?, city: CityEntity?, county: CountyEntity?
    ) {
        LogUtil.v(SunnyWeatherApplication.TestToken,"the address is "+province+city+county)
        val provinceName=province?.name
        val cityName=city?.name
        picker.setDefaultValue(province, city, county)
        nowViewModel.location.value= if (isProvince(provinceName!!)) cityName else provinceName
        nowViewModel.refreshData()
        val editor=locationRegister.edit()
        editor.putString("provinceName",provinceName)
        editor.putString("cityName",cityName)
        editor.commit()
    }

    override fun onClick(v: View?) {
        picker.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient.stopLocation()
    }
}