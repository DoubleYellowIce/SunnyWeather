package com.sunnyweather.di

import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.main.MainActivity
import com.sunnyweather.main.MainContract
import com.sunnyweather.main.MainViewModel
import dagger.Module
import dagger.Provides
import data.weather.repository.Repository

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(mainActivity: MainActivity, repository: Repository): MainViewModel {
        val mainViewModel = ViewModelProvider(mainActivity).get(MainViewModel::class.java)
        mainViewModel.repository = repository
        return mainViewModel
    }

    @Provides
    fun provideMainContractView(mainActivity: MainActivity): MainContract.View {
        return mainActivity
    }


}
