package com.example.restaurants.presentation.main.pages.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.domain.location.LocationInteractor
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.venue.VenueInteractor
import com.example.restaurants.domain.venue.model.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationInteractor: LocationInteractor,
    private val venueInteractor: VenueInteractor
) : ViewModel() {

    private val log by logger("MapViewModel")

    private val _locationLiveData = MutableLiveData<LocationLatLng>()
    val locationLiveData: LiveData<LocationLatLng> = _locationLiveData

    private val _venusLiveData = MutableLiveData<List<Venue>>()
    val venusLiveData: LiveData<List<Venue>> = _venusLiveData

    init {
        log.debug("init")
    }

    fun onMapReady() {
        subscribeLocationUpdates()
    }

    private fun subscribeLocationUpdates() {
        val locationErrorHandler = CoroutineExceptionHandler { _, throwable ->
            log.warn("Error while observing location: ", throwable)
        }
        viewModelScope.launch(locationErrorHandler) {
            locationInteractor.observeCurrentLocation()
                .onEach { location -> searchFoodVenues(location.latitude, location.longitude) }
                .collect { location -> _locationLiveData.value = location }
        }
    }

    private fun searchFoodVenues(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val venues = venueInteractor.searchVenues(latitude, longitude)
            log.debug("Venues: $venues")
        }
    }

}