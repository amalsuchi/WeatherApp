package com.example.myweather.presentation.theme.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.Domain.util.Resource
import com.example.myweather.Domain.util.WeatherRepository
import com.example.myweather.Domain.util.location.LocationTracer
import com.example.myweather.Domain.util.weather.weatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
// a ViewModel, which is used to store and manage UI-related data in a way
// that survives configuration changes like screen rotations.
class WeatherViewModel @Inject constructor(
    val repository: WeatherRepository,
    val locationTracer: LocationTracer
) :ViewModel(){

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        //coroutine
        viewModelScope.launch {
            //copy state with some changes
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracer.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message

                        )
                    }


                }

            }
                ?: kotlin.run {
                    state = state.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }
        }
    }
}