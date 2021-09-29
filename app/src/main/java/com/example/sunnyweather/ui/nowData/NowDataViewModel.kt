package com.example.sunnyweather.ui.nowData

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.example.sunnyweather.LogUtil
import com.example.sunnyweather.R
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.network.NowResponse
import com.example.sunnyweather.logic.network.Repository

class NowDataViewModel :ViewModel(),LifecycleObserver{
     val nowData= MutableLiveData<NowResponse.Result>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun  refreshData(){
        LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel ON_RESUME")
        Repository.searchNow("beijing",nowData)
    }

}