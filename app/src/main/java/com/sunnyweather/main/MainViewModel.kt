package com.sunnyweather.main

import androidx.lifecycle.*
import com.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.logic.network.Repository
import data.weather.json.DailyResponse
import data.weather.json.NowResponse
import data.weather.json.SuggestionResponse
import utils.LogUtil

class MainViewModel : ViewModel(), LifecycleObserver, MainContract.ViewModel {

    lateinit var repository: Repository

    val nowData = MutableLiveData<NowResponse.Result>()

    val dailyData = MutableLiveData<DailyResponse.Result>()

    val suggestionData = MutableLiveData<SuggestionResponse.Result>()

    val location = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refreshData() {
        LogUtil.v(SunnyWeatherApplication.TestToken, "nowViewModel ON_RESUME")
        val location = location.value
        repository.searchNow(location!!, nowData)
        repository.searchDaily(location, dailyData)
        repository.searchSuggestion(location, suggestionData)
    }

    override fun getUserLocation() {
        TODO("Not yet implemented")
    }
}
