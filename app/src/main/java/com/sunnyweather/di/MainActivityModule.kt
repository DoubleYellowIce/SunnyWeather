package com.sunnyweather.di

import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.logic.network.Repository
import com.sunnyweather.main.MainActivity
import com.sunnyweather.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(mainActivity: MainActivity, repository: Repository): MainViewModel {
        val mainViewModel = ViewModelProvider(mainActivity).get(MainViewModel::class.java)
        mainViewModel.repository = repository
        return mainViewModel
    }
}
