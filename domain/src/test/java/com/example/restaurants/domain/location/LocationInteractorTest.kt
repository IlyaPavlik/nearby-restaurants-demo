package com.example.restaurants.domain.location

import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import com.example.restaurants.domain.location.repository.LocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationInteractorTest {

    private val testLocationRepository = object : LocationRepository {
        override fun observeCurrentLocation(type: LocationObservationType): Flow<LocationLatLng> =
            flow {
                val location = when (type) {
                    LocationObservationType.TIME_INTERVAL -> MOCK_TIME_INTERVAL_LOCATION
                    LocationObservationType.DISPLACEMENT -> MOCK_DISPLACEMENT_LOCATION
                }
                emit(location)
            }
    }
    private val locationInteractor = LocationInteractor(testLocationRepository)

    @Test
    fun locationInteractor_ObserveCurrentLocation_TimeInterval() = runBlockingTest {
        val location =
            locationInteractor.observeCurrentLocation(LocationObservationType.TIME_INTERVAL)
                .first()

        assertEquals(location, MOCK_TIME_INTERVAL_LOCATION)
    }

    @Test
    fun locationInteractor_ObserveCurrentLocation_Displacement() = runBlockingTest {
        val location =
            locationInteractor.observeCurrentLocation(LocationObservationType.DISPLACEMENT)
                .first()

        assertEquals(location, MOCK_DISPLACEMENT_LOCATION)
    }

    companion object {

        private val MOCK_TIME_INTERVAL_LOCATION = LocationLatLng(1.0, 1.0)
        private val MOCK_DISPLACEMENT_LOCATION = LocationLatLng(2.0, 2.0)

    }

}