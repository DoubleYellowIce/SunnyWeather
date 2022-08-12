package com.sunnyweather.di

import android.app.Application
import com.sunnyweather.SunnyWeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import data.di.RepositoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ActivityBindingModule::class,
        NetWorkModule::class,
        WeatherModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<SunnyWeatherApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}