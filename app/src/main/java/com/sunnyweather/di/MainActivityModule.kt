package com.sunnyweather.di

import androidx.lifecycle.ViewModelProvider
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import com.github.gzuliyujiang.wheelpicker.utility.AddressJsonParser
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

    @Provides
    fun provideAddressPicker(mainActivity: MainActivity): AddressPicker {
        return AddressPicker(mainActivity).apply {
            setAddressMode(
                "city.json", AddressMode.PROVINCE_CITY,
                AddressJsonParser.Builder()
                    .provinceCodeField("code")
                    .provinceNameField("name")
                    .provinceChildField("city")
                    .cityCodeField("code")
                    .cityNameField("name")
                    .cityChildField("area")
                    .countyCodeField("code")
                    .countyNameField("name")
                    .build()
            )
            setOnAddressPickedListener(mainActivity)
        }
    }
}
