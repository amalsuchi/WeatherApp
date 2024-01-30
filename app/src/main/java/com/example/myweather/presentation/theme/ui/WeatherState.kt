package com.example.myweather.presentation.theme.ui

import com.example.myweather.Domain.util.weather.weatherInfo

data class WeatherState(
    val weatherInfo: weatherInfo? =null,
    val isLoading : Boolean = false,
    val error:String? =null
)
