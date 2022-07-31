package com.sunnyweather.di

import android.app.Application
import com.sunnyweather.SunnyWeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        MainModule::class,
        ActivityBindingModule::class]
)
interface ApplicationComponent : AndroidInjector<SunnyWeatherApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}