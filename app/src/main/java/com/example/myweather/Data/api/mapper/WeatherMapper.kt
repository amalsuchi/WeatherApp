package com.example.myweather.Data.api.mapper

import android.util.Log
import com.example.myweather.Data.api.weather.WeatherDataDto
import com.example.myweather.Data.api.weather.WeatherDto
import com.example.myweather.Domain.util.weather.WeatherData
import com.example.myweather.Domain.util.weather.WeatherType
import com.example.myweather.Domain.util.weather.weatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeaterData(
    val index : Int,
    val data: WeatherData
)
//converts WeatherDataDto objects to a Map where the key is an Int and the value
//is a List of WeatherData objects.

//Extension functions allow you to "extend" a class with new functionality
// without having to inherit from the class

//to convert a WeatherDataDto object into a to WeatherData object.
fun WeatherDataDto.toWeatherDataMap():Map<Int,List<WeatherData>>{
//function transforms each element of the time list in the WeatherDataDto object
    return time.mapIndexed{ index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure =pressures[index]
        val humidity = humidities[index]
        IndexedWeaterData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                //collected from weathertype
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
        //This line is grouping the IndexedWeaterData objects by the result of
    // dividing their index by 24.
    }.groupBy{
        it.index /24
        // This line is transforming the values of the map (which are lists of
    // IndexedWeaterData objects) into lists of WeatherData objects.
    }.mapValues {
        it.value.map {
            it.data
        }
    }.also { map ->
        for ((key, value) in map) {
            Log.d("WeatherDataMap", "Key: $key, Value: $value")
        }
    }
}

fun WeatherDto.toWeatherInfo(): weatherInfo{
    //toWeatherDataMap converts the object to a map
    //this is weatherdata for a day i.e that number that is gtouped
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now =LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find{
        val hour = if(now.minute < 30) now.hour else if(now.hour ==23 && now.minute>30) 0 else now.hour + 1
        it.time.hour == hour
    }
    return weatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeather = currentWeatherData
    )
}

//create repository
//create interface so that repository shouldn't directly interact with data in domain