package com.example.myweather.Data.api.Location

import android.Manifest
import android.app.Application
import android.content.Context

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.myweather.Domain.util.location.LocationTracer

import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class LocationTrackerImpl @Inject constructor(
    //FusedLocationProviderClient is a client library for Google Play services
    // that provides a simple and consistent API for getting the device's location.
    private val locationClient:FusedLocationProviderClient,
    private val application: Application

):LocationTracer {


    override suspend fun getCurrentLocation(): Location? {
        //checking permission
        val hasCorarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==PackageManager.PERMISSION_GRANTED

        val hasFineLocationPermission =ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) ==PackageManager.PERMISSION_GRANTED

        //to enable gps
        //getting the system service for location
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //This line is checking if either the network provider or the GPS provider is enabled
        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if(!hasCorarseLocationPermission||!hasFineLocationPermission||!isGpsEnable){
            return null
        }

        //used for ->suspendCancellableCoroutine is used here to wait for the
        // location data from the FusedLocationProviderClient API.

        //function in Kotlin that lets you pause a piece of code
        // (called a coroutine) until something else happens.
        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if(isComplete) {
                    if(isSuccessful) {
                        //resume the corutine
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }//statement is used to exit the suspendCancellableCoroutine block early.
                    return@suspendCancellableCoroutine
                }
                // sets up a listener that is called when the location request is successful.
                addOnSuccessListener {
                    cont.resume(it)
                    Log.d("TAG","s")
                }
                // sets up a listener that is called when the location request is fails.
                addOnFailureListener {
                    cont.resume(null)
                    Log.d("TAG","F")
                }
                // sets up a listener that is called when the location request is cancelled
                addOnCanceledListener {
                    cont.cancel()
                    Log.d("TAG","C")
                }
            }
        }
    }
}
//dependency injection next