package com.example.restaurants.domain.venue

import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.repository.VenueRepository
import javax.inject.Inject

class VenueInteractor @Inject constructor(
    private val venueRepository: VenueRepository
) {

    suspend fun searchVenues(latitude: Double, longitude: Double) =
        venueRepository.findVenues(VenueCategory.FOOD, latitude, longitude, 250)

}