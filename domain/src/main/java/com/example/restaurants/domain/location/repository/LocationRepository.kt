package com.example.restaurants.domain.location.repository

import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun observeCurrentLocation(type: LocationObservationType): Flow<LocationLatLng>

}