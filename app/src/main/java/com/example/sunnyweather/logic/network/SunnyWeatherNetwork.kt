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

    fun searchNow(location :String,result: MutableLiveData<NowResponse.Result>){
        nowService.searchNow(location).enqueue(object :Callback<NowResponse>{
            override fun onResponse(call: Call<NowResponse>, response: Response<NowResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onResponse")
                if (response.body()!=null){
                    result.value=response.body()!!.results[0]
                    Log.d(SunnyWeatherApplication.TestToken, result.value!!.now.temperature)
                }else if (response.errorBody()!=null){
                    LogUtil.v(SunnyWeatherApplication.TestToken,"The location can not be found")
                }
            }
            override fun onFailure(call: Call<NowResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
            }
        })
    }

//    fun <T> searchNow(parameter: String,result: MutableLiveData<T>,method :String){
//
//        when(method){
//            "search"->service= nowService
//        }
//    }


//    suspend fun searchNow(location:String)= nowService.searchNow(location).await()
//
//    private suspend fun <T> Call<T>.await(): T {
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T> {
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    Log.d(SunnyWeatherApplication.TestToken,"onResponse")
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else continuation.resumeWithException(
//                        RuntimeException("response body is null"))
//                }
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    Log.d(SunnyWeatherApplication.TestToken,"onFailure")
//                    continuation.resumeWithException(t)
//                }
//            })
//        }
//    }
}

