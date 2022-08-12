package data.weather.repository

import data.weather.json.CurrentWeather
import data.weather.json.WeatherForecast
import data.weather.json.WeatherSuggestion

interface WeatherRepository {

    suspend fun getCurrentWeatherInfo(location: String): CurrentWeather

    suspend fun getWeatherSuggestion(location: String): WeatherSuggestion

    suspend fun getWeatherForecast(location: String): WeatherForecast
}