package com.example.sunnyweather.ui.nowData


import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.network.DailyResponse
import com.example.sunnyweather.logic.network.NowResponse
import com.example.sunnyweather.logic.network.Repository
import com.example.sunnyweather.logic.network.SuggestionResponse


class NowDataViewModel :ViewModel(),LifecycleObserver{
    val nowData= MutableLiveData<NowResponse.Result>()

    val dailyData=MutableLiveData<DailyResponse.Result>()

    val suggestionData=MutableLiveData<SuggestionResponse.Result>()

    lateinit var connectivityManager:ConnectivityManager

    lateinit var activeNetwork: Network

    lateinit var locationManager:LocationManager

    val location=MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.M)
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initial() {
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_CREATE")
        locationManager=SunnyWeatherApplication.context.getSystemService(LocationManager::class.java)
        val providerEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (providerEnabled){
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener)
        }else{

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun  refreshData(){
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_RESUME")
        connectivityManager= SunnyWeatherApplication.context.getSystemService(ConnectivityManager::class.java)
        activeNetwork=connectivityManager.activeNetwork!!
        val caps=connectivityManager.getNetworkCapabilities(activeNetwork)
        if (caps!=null){
            LogUtil.v(SunnyWeatherApplication.TestToken,caps.toString())
            LogUtil.v(SunnyWeatherApplication.TestToken,caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_SUPL).toString())
        }
        if (!caps!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
            
        }else{
            val location=location.value
            Repository.searchNow(location!!,nowData)
            Repository.searchDaily(location!!,dailyData)
            Repository.searchSuggestion(location!!,suggestionData)
        }
    }

    private val locationListener=LocationListener{
        LogUtil.d(SunnyWeatherApplication.TestToken," the latitude is "+it.latitude.toString())
        LogUtil.d(SunnyWeatherApplication.TestToken," the longitude is "+it.longitude.toString())

    }

}
