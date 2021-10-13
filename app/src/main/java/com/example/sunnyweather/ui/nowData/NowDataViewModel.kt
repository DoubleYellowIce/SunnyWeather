package com.example.sunnyweather.ui.nowData


import android.app.Activity
import androidx.lifecycle.*
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.network.DailyResponse
import com.example.sunnyweather.logic.network.NowResponse
import com.example.sunnyweather.logic.network.Repository
import com.example.sunnyweather.logic.network.SuggestionResponse


class NowDataViewModel :ViewModel(),LifecycleObserver{
    val nowData= MutableLiveData<NowResponse.Result>()

    val dailyData=MutableLiveData<DailyResponse.Result>()

    val suggestionData=MutableLiveData<SuggestionResponse.Result>()

    val location=MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun  refreshData(){
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_RESUME")
        val location=location.value
        Repository.searchNow(location!!,nowData)
        Repository.searchDaily(location!!,dailyData)
        Repository.searchSuggestion(location!!,suggestionData)
    }

}
