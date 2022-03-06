package com.example.sunnyweather.logic.network.service

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.SuggestionResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call


interface SuggestionService {

    @GET("v3/life/suggestion.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans")
    fun searchSuggestion(@Query("location") location:String) : Call<SuggestionResponse>

}