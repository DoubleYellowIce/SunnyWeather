package com.sunnyweather.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import data.Response
import data.weather.model.CombineWeatherInfo
import data.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MainViewModel : ViewModel(), LifecycleObserver, MainContract.ViewModel {

    lateinit var weatherRepository: WeatherRepository

    val currentLocation = MutableLiveData<String>()

    override fun getUserLocation() {
        TODO("Not yet implemented")
    }

    override fun getCurrentLocationCombineWeatherInfo() =
        liveData(Dispatchers.IO) {
            emit(Response.loading(data = null))
            try {
                coroutineScope {
                    val currentWeather = async(Dispatchers.IO) {
                        weatherRepository.getCurrentWeatherInfo(currentLocation.value!!)
                    }
                    val weatherSuggestion = async(Dispatchers.IO) {
                        weatherRepository.getWeatherSuggestion(currentLocation.value!!)
                    }
                    val weatherForecast = async(Dispatchers.IO) {
                        weatherRepository.getWeatherForecast(currentLocation.value!!)
                    }
                    emit(
                        Response.success(
                            CombineWeatherInfo(
                                currentWeather.await(),
                                weatherForecast.await(),
                                weatherSuggestion.await()
                            )
                        )
                    )
                }
            } catch (exception: Exception) {
                emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
}



