package com.sunnyweather.main

import androidx.lifecycle.LiveData
import data.Response
import data.weather.model.CombineWeatherInfo

interface MainContract {

    interface View {
    }

    interface ViewModel {

        fun getCurrentLocationCombineWeatherInfo(): LiveData<Response<CombineWeatherInfo>>

        fun retrieveProvinceAndCityFromSP()

        fun updateProvinceAndCity(province: String, city: String)
    }
}