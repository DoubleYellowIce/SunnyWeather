package data.sharepreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharePreferencesManager @Inject constructor(private val application: Application) {

    companion object {
        const val APP_PREFERENCE_NAME = "SunnyWeather"
        const val PROVINCE = "province"
        const val CITY = "city"
    }

    private fun getAppSharedPreference(): SharedPreferences {
        return application.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getProvince(): String? {
        return getAppSharedPreference().getString(PROVINCE, "北京市")
    }

    fun getCity(): String? {
        return getAppSharedPreference().getString(CITY, "北京市")
    }

    fun setProvinceAndCity(province: String, city: String) {
        getAppSharedPreference().edit().putString(PROVINCE, province).putString(CITY, city).apply()
    }

}