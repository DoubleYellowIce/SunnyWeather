package com.sunnyweather.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import data.Response
import data.sharepreferences.SharePreferencesManager
import data.weather.model.CombineWeatherInfo
import data.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(), LifecycleObserver, MainContract.ViewModel {

    lateinit var weatherRepository: WeatherRepository

    lateinit var sharePreferencesManager: SharePreferencesManager

    val currentCity = MutableLiveData<String>()

    private val currentProvince = MutableLiveData<String>()

    override fun retrieveProvinceAndCityFromSP() {
        updateProvinceAndCity(
            sharePreferencesManager.getProvince()!!,
            sharePreferencesManager.getCity()!!
        )
    }

    override fun updateProvinceAndCity(province: String, city: String) {
        if (isProvinceName(province)) {
            setCurrentProvince(province)
        } else {
            setCurrentProvince(city)
        }
        setCurrentCity(city)
        sharePreferencesManager.setProvinceAndCity(province, city)
    }

    override fun getCurrentLocationCombineWeatherInfo() =
        liveData(Dispatchers.IO) {
            emit(Response.loading(data = null))
            try {
                coroutineScope {
                    val currentWeather = async(Dispatchers.IO) {
                        weatherRepository.getCurrentWeatherInfo(currentCity.value!!)
                    }
                    val weatherSuggestion = async(Dispatchers.IO) {
                        weatherRepository.getWeatherSuggestion(currentCity.value!!)
                    }
                    val weatherForecast = async(Dispatchers.IO) {
                        weatherRepository.getWeatherForecast(currentCity.value!!)
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

    private fun setCurrentProvince(province: String) {
        currentProvince.value = province
    }

    fun getCurrentProvinceValue(): String {
        return currentProvince.value ?: ""
    }

    fun getCurrentCityValue(): String {
        return currentCity.value ?: ""
    }

    private fun setCurrentCity(city: String) {
        currentCity.value = city
    }

    //when the provinceName is one of "北京市","上海市","天津市","重庆市"
    //the provinceName is actually a cityName
    //we should treat it as cityName to get the weather information from the internet
    private fun isProvinceName(provinceName: String): Boolean {
        return when (provinceName) {
            "北京市", "上海市", "天津市", "重庆市" -> {
                false
            }
            else -> true
        }
    }
}



