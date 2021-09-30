package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call


interface SuggestionService {

    @GET("v3/life/suggestion.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans")
    fun searchSuggestion(@Query("location") location:String) : Call<SuggestionResponse>

}