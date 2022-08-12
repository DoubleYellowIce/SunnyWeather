package data.weather.model

import data.weather.json.CurrentWeather
import data.weather.json.WeatherForecast
import data.weather.json.WeatherSuggestion

class CombineWeatherInfo(
    val currentWeather: CurrentWeather,
    val weatherForecast: WeatherForecast,
    val weatherSuggestion: WeatherSuggestion
) : BasicWeatherInfo() {
}