package com.sunnyweather.di

import dagger.Module
import dagger.Provides
import data.weather.repository.WeatherCloudDataStore
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named

@Module
class WeatherModule {

    @Provides
    fun provideWeatherCloudDataStore(@Named("weather") retrofit: Retrofit): WeatherCloudDataStore {
        return retrofit.create<WeatherCloudDataStore>()
    }

}