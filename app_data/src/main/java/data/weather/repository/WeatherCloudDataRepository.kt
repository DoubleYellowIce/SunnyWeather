package data.weather.repository

import data.weather.json.CurrentWeather
import data.weather.json.WeatherForecast
import data.weather.json.WeatherSuggestion
import javax.inject.Inject

class WeatherCloudDataRepository @Inject constructor() : WeatherRepository {


    @Inject
    lateinit var weatherCloudDataStore: WeatherCloudDataStore


    override suspend fun getCurrentWeatherInfo(location: String): CurrentWeather {
        return weatherCloudDataStore.getCurrentWeather(location)
    }

    override suspend fun getWeatherSuggestion(location: String): WeatherSuggestion {
        return weatherCloudDataStore.getWeatherSuggestion(location)
    }

    override suspend fun getWeatherForecast(location: String): WeatherForecast {
        return weatherCloudDataStore.getWeatherForecast(location)
    }

}

