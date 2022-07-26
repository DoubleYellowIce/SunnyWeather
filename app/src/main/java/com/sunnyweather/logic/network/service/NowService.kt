package com.sunnyweather.logic.network.service

import com.sunnyweather.SunnyWeatherApplication
import data.Weather.NowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NowService {

    @GET("v3/weather/now.json?key=${SunnyWeatherApplication.TOKEN}&language=zh-Hans&unit=c")
    fun searchNow(@Query("location") location: String) :Call<NowResponse>


}