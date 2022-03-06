package com.example.sunnyweather.logic.network.service

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.NowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NowService {

    @GET("v3/weather/now.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans&unit=c")
    fun searchNow(@Query("location") location: String) :Call<NowResponse>


}