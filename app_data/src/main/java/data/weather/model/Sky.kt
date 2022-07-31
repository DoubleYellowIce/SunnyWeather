package data.weather.model

import com.example.app_data.R
//the aim of this class is to map the weather code to the Sky class which contains the background image and icon image
//if you want to know more specifically,please check the Xinzhi Weather API(the link below)
//https://seniverse.yuque.com/books/share/e52aa43f-8fe9-4ffa-860d-96c0f3cf1c49/yev2c3
//the letter i in the name of icon image stands for icon

data class Sky(val bg: Int, val icon: Int)
private val sky = mapOf(
    "0" to Sky(R.drawable.bg_clear_day, R.drawable.i0),
    "1" to Sky(R.drawable.bg_clear_night, R.drawable.i1),
    "2" to Sky(R.drawable.bg_clear_day, R.drawable.i2),
    "3" to Sky(R.drawable.bg_clear_night, R.drawable.i3),
    "4" to Sky(R.drawable.bg_cloudy, R.drawable.i4),
    "5" to Sky(R.drawable.bg_partly_cloudy_day, R.drawable.i5),
    "6" to Sky(R.drawable.bg_partly_cloudy_night, R.drawable.i6),
    "7" to Sky(R.drawable.bg_partly_cloudy_day, R.drawable.i7),
    "8" to Sky(R.drawable.bg_partly_cloudy_night, R.drawable.i8),
    "9" to Sky(R.drawable.bg_cloudy, R.drawable.i9),
    "10" to Sky(R.drawable.bg_rain, R.drawable.i10),
    "11" to Sky(R.drawable.bg_rain, R.drawable.i11),
    "12" to Sky(R.drawable.bg_rain, R.drawable.i12),
    "13" to Sky(R.drawable.bg_rain, R.drawable.i13),
    "14" to Sky(R.drawable.bg_rain, R.drawable.i14),
    "15" to Sky(R.drawable.bg_rain, R.drawable.i15),
    "16" to Sky(R.drawable.bg_rain, R.drawable.i16),
    "17" to Sky(R.drawable.bg_rain, R.drawable.i17),
    "18" to Sky(R.drawable.bg_rain, R.drawable.i18),
    "19" to Sky(R.drawable.bg_rain, R.drawable.i19),
    "20" to Sky(R.drawable.bg_snow, R.drawable.i20),
    "21" to Sky(R.drawable.bg_snow, R.drawable.i21),
    "22" to Sky(R.drawable.bg_snow, R.drawable.i22),
    "23" to Sky(R.drawable.bg_snow, R.drawable.i23),
    "24" to Sky(R.drawable.bg_snow, R.drawable.i24),
    "25" to Sky(R.drawable.bg_snow, R.drawable.i25),
    "26" to Sky(R.drawable.bg_fog, R.drawable.i26),
    "27" to Sky(R.drawable.bg_fog, R.drawable.i27),
    "28" to Sky(R.drawable.bg_fog, R.drawable.i28),
    "29" to Sky(R.drawable.bg_fog, R.drawable.i29),
    "30" to Sky(R.drawable.bg_fog, R.drawable.i30),
    "31" to Sky(R.drawable.bg_fog, R.drawable.i31),
    "32" to Sky(R.drawable.bg_wind, R.drawable.i32),
    "33" to Sky(R.drawable.bg_wind, R.drawable.i33),
    "34" to Sky(R.drawable.bg_wind, R.drawable.i34),
    "35" to Sky(R.drawable.bg_wind, R.drawable.i35),
    "36" to Sky(R.drawable.bg_wind, R.drawable.i36),
    "37" to Sky(R.drawable.bg_wind, R.drawable.i37),
    "38" to Sky(R.drawable.bg_wind, R.drawable.i38),
    "99" to Sky(R.drawable.bg_clear_day, R.drawable.i99),
)

fun getSky(weatherCode: String): Sky {
    return sky[weatherCode] ?: sky["0"]!!
}