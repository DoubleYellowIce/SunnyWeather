package com.sunnyweather.logic.network

import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.main.refreshDataHandler
import data.weather.WeatherCloudDataStore
import data.weather.json.DailyResponse
import data.weather.json.NowResponse
import data.weather.json.SuggestionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.LogUtil
import javax.inject.Inject

class SunnyWeatherNetwork @Inject constructor() {


    @Inject
    lateinit var weatherCloudDataStore: WeatherCloudDataStore

    //the app get nowResponse,dailyResponse and suggestionResponse at the same time
    //so theoretically,if one function get the response successfully,the other two is likely successful too
    //vice versa
    //so we only need to send the message once
    fun searchNow(location: String, result: MutableLiveData<NowResponse.Result>) {
        weatherCloudDataStore.searchNow(location).enqueue(object : Callback<NowResponse> {
            override fun onResponse(call: Call<NowResponse>, response: Response<NowResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken, "searchNow.onResponse")
                val message = Message.obtain()
                if (response.body() != null) {
                    result.value = response.body()!!.results[0]
                    Log.d(
                        SunnyWeatherApplication.TestToken,
                        "The temperature is ${result.value!!.now.temperature}"
                    )
                    message.obj = "refreshDataSuccessfully"
                } else if (response.errorBody() != null) {
                    LogUtil.v(
                        SunnyWeatherApplication.TestToken,
                        "There is something wrong,please check"
                    )
                    message.obj = "failToRefreshData"
                }
                refreshDataHandler.sendMessage(message)
            }
            override fun onFailure(call: Call<NowResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken,"onFailure")
                val message=Message.obtain()
                message.obj="networkIsNotWorking"
                refreshDataHandler.sendMessage(message)

            }
        })
    }



    fun searchDaily(location: String,result: MutableLiveData<DailyResponse.Result>){
        weatherCloudDataStore.searchDaily(location).enqueue(object : Callback<DailyResponse> {
            override fun onResponse(call: Call<DailyResponse>, response: Response<DailyResponse>) {
                LogUtil.v(SunnyWeatherApplication.TestToken, "searchDaily.onResponse")
                if (response.body() != null) {
                    result.value = response.body()!!.results[0]
                } else if (response.errorBody() != null) {
                    LogUtil.v(
                        SunnyWeatherApplication.TestToken,
                        "There is something wrong,please check"
                    )
                }
            }

            override fun onFailure(call: Call<DailyResponse>, t: Throwable) {
                LogUtil.v(SunnyWeatherApplication.TestToken, "onFailure")
            }
        })
    }

    fun searchSuggestion(location: String,result: MutableLiveData<SuggestionResponse.Result>){
        weatherCloudDataStore.searchSuggestion(location)
            .enqueue(object : Callback<SuggestionResponse> {
                override fun onResponse(
                    call: Call<SuggestionResponse>,
                    response: Response<SuggestionResponse>
                ) {
                    LogUtil.v(SunnyWeatherApplication.TestToken, "searchSuggestion.onResponse")
                    if (response.body() != null) {
                        result.value = response.body()!!.results[0]
                    } else if (response.errorBody() != null) {
                        LogUtil.v(
                            SunnyWeatherApplication.TestToken,
                            "There is something wrong,please check"
                        )
                    }
                }

                override fun onFailure(call: Call<SuggestionResponse>, t: Throwable) {
                    LogUtil.v(SunnyWeatherApplication.TestToken, "onFailure")
            }
        })
    }

}

