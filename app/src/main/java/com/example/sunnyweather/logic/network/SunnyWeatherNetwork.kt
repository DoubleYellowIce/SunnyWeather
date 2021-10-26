package com.example.sunnyweather.logic.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.network.SuggestionResponse.Suggestion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SunnyWeatherNetwork {
    private val nowService =ServiceCreator.create<NowService>()

    private val dailyService=ServiceCreator.create<DailyService>()

    private val suggestionService=ServiceCreator.create<SuggestionService>()

    fun searchNow(location :String,result: MutableLiveData<NowResponse.Result>){
        nowService.searchNow(location).enqueue(object :Callback<NowResponse>{
            override fun onResponse(call: Call<NowResponse>, response: Response<NowResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"searchNow.onResponse")
                if (response.body()!=null){
                    result.value=response.body()!!.results[0]
                    Log.d(SunnyWeatherApplication.TestToken,"The temperature is ${result.value!!.now.temperature}")
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

    fun searchSuggestion(location: String,result: MutableLiveData<SuggestionResponse.Result>){
        suggestionService.searchSuggestion(location).enqueue(object :Callback<SuggestionResponse>{
            override fun onResponse(call: Call<SuggestionResponse>, response: Response<SuggestionResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"searchSuggestion.onResponse")
                if (response.body()!=null){
//                    var suggestion=response.body()!!.results[0].suggestion

                    result.value=response.body()!!.results[0]
                }else if (response.errorBody()!=null){
                    LogUtil.v(SunnyWeatherApplication.TestToken,"There is something wrong,please check")
                }
            }
            override fun onFailure(call: Call<SuggestionResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
            }
        })
    }

}

