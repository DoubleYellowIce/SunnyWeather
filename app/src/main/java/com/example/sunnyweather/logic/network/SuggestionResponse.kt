package com.example.sunnyweather.logic.network

data class SuggestionResponse(val results: List<Result>) {

    data class Result(val location: Location,val suggestion:Suggestion,val last_update:String)

    data class Location(val id:String,val name:String,val country:String,val path:String,val timezone:String,val timezone_offset:String)

    data class Suggestion (val car_washing:Car_washing,
                           val dressing: Dressing,
                           val flu:Flu,
                           val sport:Sport,
                           val travel:Travel,
                           val uv:Uv
                           )

    data class Car_washing(val brief:String,val detail:String)

    data class Dressing(val brief:String,val detail:String)

    data class Flu(val brief:String,val detail:String)

    data class Sport(val brief:String,val detail:String)

    data class Travel(val brief:String,val detail:String)

    data class Uv(val brief:String,val detail:String)


}

