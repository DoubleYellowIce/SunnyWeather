package com.sunnyweather.logic.network

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.sunnyweather.LogUtil
import com.sunnyweather.SunnyWeatherApplication
import data.Weather.DailyResponse
import data.Weather.NowResponse
import data.Weather.SuggestionResponse

object Repository {

     fun searchNow(@NonNull location: String, result: MutableLiveData<NowResponse.Result>){
        try {
            SunnyWeatherNetwork.searchNow(location,result)
            LogUtil.v(SunnyWeatherApplication.TestToken,"Repository.searchNow")
        }catch (e: Exception){

        }
    }

    fun searchDaily(@NonNull location: String,result: MutableLiveData<DailyResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken,"Repository.searchDaily")
            SunnyWeatherNetwork.searchDaily(location,result)
        }catch (e:Exception){

        }
    }

    fun searchSuggestion(@NonNull location: String,result: MutableLiveData<SuggestionResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken,"Repository.searchSuggestion")
            SunnyWeatherNetwork.searchSuggestion(location,result)
        }catch (e : Exception){

        }
    }
}