package com.example.sunnyweather.logic.network


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

object Repository {

     fun searchNow(location: String,result: MutableLiveData<NowResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken,"Repository.searchNow")
            SunnyWeatherNetwork.searchNow(location,result)
        }catch (e: Exception){

        }
    }

    fun searchDaily(location: String,result: MutableLiveData<DailyResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken,"Repository.searchDaily")
            SunnyWeatherNetwork.searchDaily(location,result)
        }catch (e:Exception){

        }

    }






}