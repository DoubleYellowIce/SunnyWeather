package com.sunnyweather.logic.network.service

import com.sunnyweather.SunnyWeatherApplication
import data.Weather.DailyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyService {

    @GET("v3/weather/daily.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans&unit=c")
    fun searchDaily(@Query("location") location:String) : Call<DailyResponse>

}