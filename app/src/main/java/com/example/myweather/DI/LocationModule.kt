package com.example.myweather.DI

import com.example.myweather.Data.api.Location.LocationTrackerImpl
import com.example.myweather.Domain.util.location.LocationTracer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    //instead of provides
    @Binds
    @Singleton
    //This abstract function tells Dagger Hilt to use LocationTrackerImpl
    // whenever a LocationTracer is requested for injection
    abstract fun bindLocationTracer(defaultLocationTrackerImpl: LocationTrackerImpl):LocationTracer
}