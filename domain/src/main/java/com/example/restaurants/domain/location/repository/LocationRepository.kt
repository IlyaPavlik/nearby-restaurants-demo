package com.example.restaurants.domain.location.repository

import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    /**
     * Observe of the user's current location
     *
     * @param type observation type
     * @return flow with current location
     */
    fun observeCurrentLocation(type: LocationObservationType): Flow<LocationLatLng>

}