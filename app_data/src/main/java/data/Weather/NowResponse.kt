package data.Weather

//contains today's weather information
data class NowResponse(val results: List<Result>)   {

    data class Result(val location: Location, val now: Now, val last_update: String)

    data class Location(val id:String,val name:String,val country:String,val path:String,val timezone:String,val timezone_offset:String)

    data class Now(val text:String,val code:String,val temperature:String)

}