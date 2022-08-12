package com.sunnyweather.di

import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.main.MainActivity
import com.sunnyweather.main.MainContract
import com.sunnyweather.main.MainViewModel
import dagger.Module
import dagger.Provides
import data.sharepreferences.SharePreferencesManager
import data.weather.repository.WeatherRepository

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(
        mainActivity: MainActivity,
        weatherRepository: WeatherRepository,
        sharePreferencesManager: SharePreferencesManager
    ): MainViewModel {
        return ViewModelProvider(mainActivity).get(MainViewModel::class.java).apply {
            this.weatherRepository = weatherRepository
            this.sharePreferencesManager = sharePreferencesManager
        }
    }

    @Provides
    fun provideMainContractView(mainActivity: MainActivity): MainContract.View {
        return mainActivity
    }


}
