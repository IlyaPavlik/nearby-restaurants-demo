package com.example.restaurants.domain.location.repository

import com.example.restaurants.domain.location.model.LocationLatLng
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun observeCurrentLocation(): Flow<LocationLatLng>

}