package com.sunnyweather.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class MainModule {

    @Provides
    @Singleton
    @Named("location")
    fun providePreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("location", Context.MODE_PRIVATE)
    }

}