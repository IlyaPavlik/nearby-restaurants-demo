package com.example.restaurants.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import com.example.restaurants.domain.location.repository.LocationRepository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private val locationIntervalRequest by lazy {
        LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    private val locationDisplacementRequest by lazy {
        LocationRequest.create().apply {
            smallestDisplacement = SMALLEST_DISPLACEMENT
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override fun observeCurrentLocation(type: LocationObservationType): Flow<LocationLatLng> =
        callbackFlow {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    trySend(
                        LocationLatLng(
                            result.lastLocation.latitude,
                            result.lastLocation.longitude
                        )
                    )
                }
            }
            val request = when (type) {
                LocationObservationType.TIME_INTERVAL -> locationIntervalRequest
                LocationObservationType.DISPLACEMENT -> locationDisplacementRequest
            }

            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose { fusedLocationProviderClient.removeLocationUpdates(locationCallback) }
        }

    companion object {

        private const val LOCATION_UPDATE_INTERVAL = 30000L //30sec
        private const val SMALLEST_DISPLACEMENT = 10F //meters

    }

}