package com.example.sunnyweather.logic.network

//it contains three-day weather forecast information
data class DailyResponse(val results :List<Result>) {

    data class Result(val location: Location,val daily:List<Daily>,val last_update:String)

    data class Location(val id:String,
                        val name:String,
                        val country:String,
                        val path:String,
                        val timezone:String,
                        val timezone_offset:String)

    data class Daily(val date:String,
                     val text_day:String,
                     val code_day:String,
                     val text_night:String,
                     val code_night:String,
                     val high:String,
                     val low:String,
                     val rainfall:String,
                     val precip:String,
                     val wind_direction:String,
                     val wind_direction_degree: String,
                     val wind_speed:String,
                     val wind_scale:String,
                     val humidity:String
    )


}