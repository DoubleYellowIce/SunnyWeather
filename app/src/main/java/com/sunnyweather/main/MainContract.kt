package com.sunnyweather.main

interface MainContract {

    interface View {
        fun handleUserLocation()
    }

    interface ViewModel {
        fun getUserLocation()
    }
}