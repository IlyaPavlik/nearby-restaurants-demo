package com.example.restaurants.presentation.main.pages.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.domain.common.architecture.SingleLiveEvent
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.domain.location.LocationInteractor
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.location.model.LocationObservationType
import com.example.restaurants.domain.venue.VenueInteractor
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.presentation.common.ext.toVenuesRegion
import com.example.restaurants.presentation.main.pages.map.model.MapErrorType
import com.google.android.gms.maps.model.VisibleRegion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
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

    private val _errorToastLiveData = SingleLiveEvent<MapErrorType>()
    val errorToastLiveData: LiveData<MapErrorType> = _errorToastLiveData

    init {
        log.debug("init")
    }

    fun onMapReady() {
        subscribeLocationUpdates()
    }

    fun onVisibleRegionChanged(visibleRegion: VisibleRegion) {
        val venuesRegion = visibleRegion.toVenuesRegion()
        val venuesErrorHandler = CoroutineExceptionHandler { _, throwable ->
            log.warn("Error while loading venues: ", throwable)
            _errorToastLiveData.value = MapErrorType.VENUES
        }

        viewModelScope.launch(venuesErrorHandler) {
            val venues = venueInteractor.searchFoodVenues(venuesRegion)
            log.debug("Region venues: ${venues.size}")
            _venusLiveData.value = venues
        }
    }

    private fun subscribeLocationUpdates() {
        val locationErrorHandler = CoroutineExceptionHandler { _, throwable ->
            log.warn("Error while observing location: ", throwable)
            _errorToastLiveData.value = MapErrorType.LOCATION
        }
        viewModelScope.launch(locationErrorHandler) {
            locationInteractor.observeCurrentLocation(LocationObservationType.DISPLACEMENT)
                .collect { location -> _locationLiveData.value = location }
        }
    }

}