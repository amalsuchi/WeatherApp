package com.example.myweather.Data.api.repository

import android.util.Log
import com.example.myweather.Data.api.WeatherApi
import com.example.myweather.Data.api.mapper.toWeatherInfo
import com.example.myweather.Domain.util.Resource
import com.example.myweather.Domain.util.WeatherRepository
import com.example.myweather.Domain.util.weather.weatherInfo
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api:WeatherApi
):WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<weatherInfo> {
        return try {
            Resource.Success(
                //taking data from api
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()

            )

        }catch (e:Exception){
            Log.e("WRI","Error fetching weather data: ${e.message}", e)
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}
//locatioin interface