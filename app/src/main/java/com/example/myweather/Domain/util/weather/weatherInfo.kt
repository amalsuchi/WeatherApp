package com.example.myweather.Domain.util.weather

//this is created to isolate the domain layer as the data layer one has moshi
//this is done so that whatever changes in data doesnt
//effect here
data class weatherInfo(
    //map for instance day 1 has these datas like that
    val weatherDataPerDay  : Map<Int,List<WeatherData>>,
    val currentWeather : WeatherData?
)
// create the map to connect in data in mapper