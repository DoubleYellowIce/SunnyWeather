package com.sunnyweather.logic.network.service

import com.sunnyweather.SunnyWeatherApplication
import data.Weather.SuggestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SuggestionService {

    @GET("v3/life/suggestion.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans")
    fun searchSuggestion(@Query("location") location:String) : Call<SuggestionResponse>

}