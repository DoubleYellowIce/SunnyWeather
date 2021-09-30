package com.example.sunnyweather.logic.network

import android.app.Service
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
object SunnyWeatherNetwork {
    private val nowService =ServiceCreator.create<NowService>()

    private val dailyService=ServiceCreator.create<DailyService>()

    fun searchNow(location :String,result: MutableLiveData<NowResponse.Result>){
        nowService.searchNow(location).enqueue(object :Callback<NowResponse>{
            override fun onResponse(call: Call<NowResponse>, response: Response<NowResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"searchNow.onResponse")
                if (response.body()!=null){
                    result.value=response.body()!!.results[0]
                    Log.d(SunnyWeatherApplication.TestToken,"The temperature is ${result.value!!.now.temperature}");
                }else if (response.errorBody()!=null){
                    LogUtil.v(SunnyWeatherApplication.TestToken,"There is something wrong,please check")
                }
            }
            override fun onFailure(call: Call<NowResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
            }
        })
    }

    fun searchDaily(location: String,result: MutableLiveData<DailyResponse.Result>){
        dailyService.searchDaily(location).enqueue(object :Callback<DailyResponse>{
            override fun onResponse(call: Call<DailyResponse>, response: Response<DailyResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"searchDaily.onResponse")
                if (response.body()!=null){
                    result.value=response.body()!!.results[0]
                }else if (response.errorBody()!=null){
                    LogUtil.v(SunnyWeatherApplication.TestToken,"There is something wrong,please check")
                }
            }
            override fun onFailure(call: Call<DailyResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
            }
        })
    }




}

