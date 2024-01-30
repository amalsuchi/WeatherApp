package com.example.myweather.Domain.util

import com.example.myweather.Domain.util.weather.weatherInfo
import retrofit2.Response

//create repository
//create interface so that repository shouldn't directly interact with data in domain
interface WeatherRepository {
    suspend fun getWeatherData(lat:Double,long:Double):Resource<weatherInfo>
}