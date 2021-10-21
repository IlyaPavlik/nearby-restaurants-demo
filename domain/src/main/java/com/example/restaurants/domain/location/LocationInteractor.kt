package com.example.restaurants.domain.location

import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import com.example.restaurants.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationInteractor @Inject constructor(
    private val locationRepository: LocationRepository
) {

    /**
     * Observe of the user's current location
     *
     * @param type observation type
     * @return flow with current location
     */
    fun observeCurrentLocation(type: LocationObservationType): Flow<LocationLatLng> =
        locationRepository.observeCurrentLocation(type)

}