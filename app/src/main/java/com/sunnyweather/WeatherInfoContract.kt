package com.sunnyweather

interface WeatherInfoContract {

    interface View {
        fun handleUserLocation()
    }

    interface ViewModel {
        fun getUserLocation()
    }
}