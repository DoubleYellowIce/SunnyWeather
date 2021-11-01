package com.example.sunnyweather.logic.network

import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.MainActivity
import com.example.sunnyweather.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SunnyWeatherNetwork {
    private val nowService =ServiceCreator.create<NowService>()

    private val dailyService=ServiceCreator.create<DailyService>()

    private val suggestionService=ServiceCreator.create<SuggestionService>()

    //the app get nowResponse,dailyResponse and suggestionResponse at the same time
    //so theoretically,if one function get the response successfully,the other two is likely successful too
    //vice versa
    //so we only need to send the message once
    fun searchNow(location :String,result: MutableLiveData<NowResponse.Result>){
        nowService.searchNow(location).enqueue(object :Callback<NowResponse>{
            override fun onResponse(call: Call<NowResponse>, response: Response<NowResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"searchNow.onResponse")
                val message=Message.obtain()
                if (response.body()!=null){
                    result.value=response.body()!!.results[0]
                    Log.d(SunnyWeatherApplication.TestToken,"The temperature is ${result.value!!.now.temperature}")
                    message.obj="refreshDataSuccessfully"
                }else if (response.errorBody()!=null){
                    LogUtil.v(SunnyWeatherApplication.TestToken,"There is something wrong,please check")
                    message.obj="failToRefreshData"
                }
                MainActivity.refreshDataHandler.sendMessage(message)
            }
            override fun onFailure(call: Call<NowResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
                val message=Message.obtain()
                message.obj="networkIsNotWorking"
                MainActivity.refreshDataHandler.sendMessage(message)
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

