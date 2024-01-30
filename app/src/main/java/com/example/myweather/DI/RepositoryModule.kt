package com.example.myweather.DI

import com.example.myweather.Data.api.Location.LocationTrackerImpl
import com.example.myweather.Data.api.repository.WeatherRepositoryImpl
import com.example.myweather.Domain.util.WeatherRepository
import com.example.myweather.Domain.util.location.LocationTracer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //instead of provides
    @Binds
    @Singleton
    //Use the WeatherRepositoryImpl class whenever someone asks for a WeatherRepository.
    // It's like saying, "If someone wants an apple, give them a Granny Smith apple."
    abstract fun bindRepositoryModule(weatherRepositoryimpl: WeatherRepositoryImpl): WeatherRepository
}
//next applicationclass