package com.example.myweather.Data.api.weather

import com.example.myweather.Data.api.weather.WeatherDataDto
import com.squareup.moshi.Json


data class WeatherDto(
    //real name is hourly -> moshi conversion
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)
