package com.sunnyweather.main

import androidx.lifecycle.LiveData
import data.Response
import data.weather.model.CombineWeatherInfo

interface MainContract {

    interface View {
        fun handleUserLocation()
    }

    interface ViewModel {
        fun getUserLocation()

        fun getCurrentLocationCombineWeatherInfo(): LiveData<Response<CombineWeatherInfo>>
    }
}