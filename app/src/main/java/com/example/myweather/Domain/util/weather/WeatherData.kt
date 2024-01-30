package com.example.myweather.Domain.util.weather

import java.time.LocalDateTime
//previously it was a list of all the things
//do it once in our mapper
data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure:Double,
    val windSpeed: Double,
    val humidity:Double,
    val weatherType:WeatherType
)
//now create a mappers take dto object and map to this domain lvl objects