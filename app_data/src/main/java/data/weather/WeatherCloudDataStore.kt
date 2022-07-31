package data.weather

import data.weather.json.DailyResponse
import data.weather.json.NowResponse
import data.weather.json.SuggestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherCloudDataStore {

    @GET("v3/weather/daily.json?key=SFopBmbAILXtq1b6K&language=zh-Hans&unit=c")
    fun searchDaily(@Query("location") location: String): Call<DailyResponse>

    @GET("v3/weather/now.json?key=SFopBmbAILXtq1b6K&language=zh-Hans&unit=c")
    fun searchNow(@Query("location") location: String): Call<NowResponse>

    @GET("v3/life/suggestion.json?key=SFopBmbAILXtq1b6K&language=zh-Hans")
    fun searchSuggestion(@Query("location") location: String): Call<SuggestionResponse>

}