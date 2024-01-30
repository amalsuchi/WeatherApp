package com.example.myweather.Domain.util.location

import android.location.Location


interface LocationTracer {
    suspend fun getCurrentLocation():Location?
}