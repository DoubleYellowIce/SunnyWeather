package com.example.sunnyweather.logic.model

import com.example.sunnyweather.R

//详情参考心知天气api
//https://seniverse.yuque.com/books/share/e52aa43f-8fe9-4ffa-860d-96c0f3cf1c49/yev2c3

class Sky ( val bg:Int,val icon: Int)
private val sky = mapOf(
    "0" to Sky( R.drawable.bg_clear_day,R.drawable.i0),
    "1" to Sky(R.drawable.bg_clear_night,R.drawable.i1),
    "2" to Sky(R.drawable.bg_clear_day,R.drawable.i2),
    "3" to Sky( R.drawable.bg_clear_night,R.drawable.i3),
    "4" to Sky( R.drawable.bg_cloudy,R.drawable.i4),
    "5" to Sky( R.drawable.bg_partly_cloudy_day,R.drawable.i5),
    "6" to Sky( R.drawable.bg_partly_cloudy_night,R.drawable.i6),
    "7" to Sky( R.drawable.bg_partly_cloudy_day,R.drawable.i7),
    "8" to Sky( R.drawable.bg_partly_cloudy_night,R.drawable.i8),
    "9" to Sky( R.drawable.bg_cloudy,R.drawable.i9),
    "10" to Sky(R.drawable.bg_rain, R.drawable.i10),
    "11" to Sky(R.drawable.bg_rain ,R.drawable.i11),
    "12" to Sky( R.drawable.bg_rain,R.drawable.i12),
    "13" to Sky( R.drawable.bg_rain,R.drawable.i13),
    "14" to Sky( R.drawable.bg_rain,R.drawable.i14),
    "15" to Sky( R.drawable.bg_rain,R.drawable.i15),
    "16" to Sky(R.drawable.bg_rain, R.drawable.i16),
    "17" to Sky( R.drawable.bg_rain,R.drawable.i17),
    "18" to Sky( R.drawable.bg_rain,R.drawable.i18),
    "19" to Sky( R.drawable.bg_rain,R.drawable.i19),
    "20" to Sky( R.drawable.bg_snow,R.drawable.i20),
    "21" to Sky( R.drawable.bg_snow,R.drawable.i21),
    "22" to Sky( R.drawable.bg_snow,R.drawable.i22),
    "23" to Sky( R.drawable.bg_snow,R.drawable.i23),
    "24" to Sky( R.drawable.bg_snow,R.drawable.i24),
    "25" to Sky( R.drawable.bg_snow,R.drawable.i25),
    "26" to Sky( R.drawable.bg_fog,R.drawable.i26),
    "27" to Sky( R.drawable.bg_fog,R.drawable.i27),
    "28" to Sky( R.drawable.bg_fog,R.drawable.i28),
    "29" to Sky( R.drawable.bg_fog,R.drawable.i29),
    "30" to Sky( R.drawable.bg_fog,R.drawable.i30),
    "31" to Sky( R.drawable.bg_fog,R.drawable.i31),
    "32" to Sky( R.drawable.bg_wind,R.drawable.i32),
    "33" to Sky( R.drawable.bg_wind,R.drawable.i33),
    "34" to Sky( R.drawable.bg_wind,R.drawable.i34),
    "35" to Sky( R.drawable.bg_wind,R.drawable.i35),
    "36" to Sky( R.drawable.bg_wind,R.drawable.i36),
    "37" to Sky( R.drawable.bg_wind,R.drawable.i37),
    "38" to Sky( R.drawable.bg_wind,R.drawable.i38),
    "99" to Sky( R.drawable.bg_clear_day,R.drawable.i99),

//    "1" to Sky("晴", R.drawable.ic_clear_night, R.drawable.bg_clear_night),
//    "5" to Sky("多云", R.drawable.ic_partly_cloud_day,
//        R.drawable.bg_partly_cloudy_day),
//    "6" to Sky("多云", R.drawable.ic_partly_cloud_night,
//        R.drawable.bg_partly_cloudy_night),
//    "9" to Sky("阴", R.drawable.ic_cloudy, R.drawable.bg_cloudy),
//    "32" to Sky("大风", R.drawable.ic_cloudy, R.drawable.bg_wind),
//    "13" to Sky("小雨", R.drawable.ic_light_rain, R.drawable.bg_rain),
//    "14" to Sky("中雨", R.drawable.ic_moderate_rain, R.drawable.bg_rain),
//    "15" to Sky("大雨", R.drawable.ic_heavy_rain, R.drawable.bg_rain),
//    "16" to Sky("暴雨", R.drawable.ic_storm_rain, R.drawable.bg_rain),
//    "11" to Sky("雷阵雨", R.drawable.ic_thunder_shower, R.drawable.bg_rain),
//    "20" to Sky("雨夹雪", R.drawable.ic_sleet, R.drawable.bg_rain),
//    "22" to Sky("小雪", R.drawable.ic_light_snow, R.drawable.bg_snow),
//    "23" to Sky("中雪", R.drawable.ic_moderate_snow, R.drawable.bg_snow),
//    "24" to Sky("大雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
//    "25" to Sky("暴雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
//    "HAIL" to Sky("冰雹", R.drawable.ic_hail, R.drawable.bg_snow),
//    "LIGHT_HAZE" to Sky("轻度雾霾", R.drawable.ic_light_haze, R.drawable.bg_fog),
//    "31" to Sky("中度雾霾", R.drawable.ic_moderate_haze, R.drawable.bg_fog),
//    "HEAVY_HAZE" to Sky("重度雾霾", R.drawable.ic_heavy_haze, R.drawable.bg_fog),
//    "30" to Sky("雾", R.drawable.ic_fog, R.drawable.bg_fog),
//    "26" to Sky("浮尘", R.drawable.ic_fog, R.drawable.bg_fog)
)
fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["0"]!!
}