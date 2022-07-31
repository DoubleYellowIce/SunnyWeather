package com.sunnyweather

import android.annotation.SuppressLint
import android.content.Context
import com.sunnyweather.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SunnyWeatherApplication : DaggerApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "SFopBmbAILXtq1b6K"
        const val TestToken = "DoubleYellowIce"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }

}