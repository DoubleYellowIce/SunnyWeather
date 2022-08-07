package data.di

import dagger.Module
import dagger.Provides
import data.weather.repository.WeatherCloudDataRepository
import data.weather.repository.WeatherRepository

@Module
class RepositoryModule {

    @Provides
    fun provideWeatherRepository(weatherCloudDataRepository: WeatherCloudDataRepository): WeatherRepository {
        return weatherCloudDataRepository
    }
}