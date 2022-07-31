package com.sunnyweather.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetWorkModule {

    @Provides
    @Singleton
    @Named("weather")
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.seniverse.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}