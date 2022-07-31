package com.sunnyweather.logic.network

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.sunnyweather.SunnyWeatherApplication
import data.weather.json.DailyResponse
import data.weather.json.NowResponse
import data.weather.json.SuggestionResponse
import utils.LogUtil
import javax.inject.Inject

class Repository @Inject constructor() {

    @Inject
    lateinit var sunnyWeatherNetwork: SunnyWeatherNetwork


    fun searchNow(@NonNull location: String, result: MutableLiveData<NowResponse.Result>) {
        try {
            sunnyWeatherNetwork.searchNow(location, result)
            LogUtil.v(SunnyWeatherApplication.TestToken, "Repository.searchNow")
        } catch (e: Exception) {

        }
    }

    fun searchDaily(@NonNull location: String, result: MutableLiveData<DailyResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken, "Repository.searchDaily")
            sunnyWeatherNetwork.searchDaily(location, result)
        }catch (e:Exception){

        }
    }

    fun searchSuggestion(@NonNull location: String,result: MutableLiveData<SuggestionResponse.Result>){
        try {
            LogUtil.v(SunnyWeatherApplication.TestToken, "Repository.searchSuggestion")
            sunnyWeatherNetwork.searchSuggestion(location, result)
        }catch (e : Exception){

        }
    }
}