package data.weather.json

import com.google.gson.annotations.SerializedName
import data.weather.model.BasicWeatherInfo

//current weather information
data class CurrentWeather(@SerializedName("results") val weather: List<Weather>) :
    BasicWeatherInfo() {

    data class Weather(
        val location: Location,
        @SerializedName("now") val detailedWeather: DetailedWeather,
        @SerializedName("last_update") val lastUpdateTime: String
    )

    data class Location(
        val id: String,
        val name: String,
        val country: String,
        val path: String,
        val timezone: String,
        @SerializedName("timeZoneOffset") val timeZoneOffset: String
    )

    data class DetailedWeather(
        @SerializedName("text") val weather: String,
        @SerializedName("code") val weatherCode: String,
        val temperature: String
    )

}