package com.sunnyweather.ui.nowData

import androidx.lifecycle.*
import com.sunnyweather.LogUtil
import com.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.WeatherInfoContract
import com.sunnyweather.logic.network.Repository
import data.Weather.DailyResponse
import data.Weather.NowResponse
import data.Weather.SuggestionResponse

class ResponseViewModel : ViewModel(), LifecycleObserver, WeatherInfoContract.ViewModel {


    val nowData = MutableLiveData<NowResponse.Result>()

    val dailyData = MutableLiveData<DailyResponse.Result>()

    val suggestionData = MutableLiveData<SuggestionResponse.Result>()

    val location = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refreshData() {
        LogUtil.v(SunnyWeatherApplication.TestToken, "nowViewModel ON_RESUME")
        val location = location.value
        Repository.searchNow(location!!, nowData)
        Repository.searchDaily(location, dailyData)
        Repository.searchSuggestion(location, suggestionData)
    }

    override fun getUserLocation() {
        TODO("Not yet implemented")
    }
}
