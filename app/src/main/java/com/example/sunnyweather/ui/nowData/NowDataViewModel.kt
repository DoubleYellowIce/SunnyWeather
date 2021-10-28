package com.example.sunnyweather.ui.nowData


import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.network.DailyResponse
import com.example.sunnyweather.logic.network.NowResponse
import com.example.sunnyweather.logic.network.Repository
import com.example.sunnyweather.logic.network.SuggestionResponse
import com.permissionx.guolindev.PermissionX
import java.util.jar.Manifest


class NowDataViewModel :ViewModel(),LifecycleObserver{

    private var providerEnabled:Boolean=false

    val nowData= MutableLiveData<NowResponse.Result>()

    val dailyData=MutableLiveData<DailyResponse.Result>()

    val suggestionData=MutableLiveData<SuggestionResponse.Result>()

    private lateinit var connectivityManager:ConnectivityManager

    private lateinit var activeNetwork: Network

    private lateinit var locationManager:LocationManager

    val location=MutableLiveData<String>()

    private val locationListener=LocationListener{

        LogUtil.d(SunnyWeatherApplication.TestToken,"LocationListener receive successfully")
        LogUtil.d(SunnyWeatherApplication.TestToken," the latitude is "+it.latitude.toString())
        LogUtil.d(SunnyWeatherApplication.TestToken," the longitude is "+it.longitude.toString())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initial() {
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_CREATE")
        locationManager=SunnyWeatherApplication.context.getSystemService(LocationManager::class.java)
        providerEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun  refreshData(){
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_RESUME")
        connectivityManager= SunnyWeatherApplication.context.getSystemService(ConnectivityManager::class.java)
        activeNetwork=connectivityManager.activeNetwork!!
        val caps=connectivityManager.getNetworkCapabilities(activeNetwork)
        if (!caps!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){

        }else{
            val location=location.value
            Repository.searchNow(location!!,nowData)
            Repository.searchDaily(location,dailyData)
            Repository.searchSuggestion(location,suggestionData)
        }
        val locationPermissionIsGranted=PermissionX.isGranted(SunnyWeatherApplication.context, ACCESS_FINE_LOCATION)
        if (providerEnabled&&locationPermissionIsGranted){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0F,locationListener)
        }else{
            Toast.makeText(SunnyWeatherApplication.context,"请打开定位权限",Toast.LENGTH_SHORT).show()
        }
    }
}
