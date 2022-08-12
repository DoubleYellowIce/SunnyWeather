package data.weather.repository

import data.weather.json.CurrentWeather
import data.weather.json.WeatherForecast
import data.weather.json.WeatherSuggestion
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherCloudDataStore {

    @GET("v3/weather/now.json?key=SFopBmbAILXtq1b6K&language=zh-Hans&unit=c")
    suspend fun getCurrentWeather(@Query("location") location: String): CurrentWeather

    @GET("v3/life/suggestion.json?key=SFopBmbAILXtq1b6K&language=zh-Hans")
    suspend fun getWeatherSuggestion(@Query("location") location: String): WeatherSuggestion

    @GET("v3/weather/daily.json?key=SFopBmbAILXtq1b6K&language=zh-Hans&unit=c")
    suspend fun getWeatherForecast(@Query("location") location: String): WeatherForecast
}