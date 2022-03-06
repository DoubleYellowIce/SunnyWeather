package com.example.sunnyweather.logic.network.service

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.DailyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyService {

    @GET("v3/weather/daily.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans&unit=c")
    fun searchDaily(@Query("location") location:String) : Call<DailyResponse>

}