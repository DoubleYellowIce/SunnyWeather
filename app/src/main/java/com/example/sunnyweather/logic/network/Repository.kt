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

//    fun searchNow(location: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            Log.d(SunnyWeatherApplication.TestToken,"liveData")
//            val nowResponse = SunnyWeatherNetwork.searchNow(location)
//            Result.success(nowResponse)
//        } catch (e: Exception) {
//            Result.failure<NowResponse>(e)
//        }
//        emit(result)
//    }



}