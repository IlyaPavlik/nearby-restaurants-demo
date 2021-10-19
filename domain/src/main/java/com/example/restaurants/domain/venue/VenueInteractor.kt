package com.example.restaurants.domain.venue

import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.model.VenuesRegion
import com.example.restaurants.domain.venue.repository.VenueRepository
import javax.inject.Inject

class VenueInteractor @Inject constructor(private val venueRepository: VenueRepository) {

    private val log by logger("VenueInteractor")

    suspend fun searchFoodVenues(venuesRegion: VenuesRegion): List<Venue> {
        val category = VenueCategory.FOOD
        val cachedVenues = venueRepository.findCachedVenues(category, venuesRegion)

        return if (cachedVenues.isNotEmpty()) {
            cachedVenues
                .also { log.debug("Loaded cached data") }
        } else {
            venueRepository.findRemoteVenues(category, venuesRegion)
                .also { venues ->
                    venueRepository.saveVenuesToCache(category, venuesRegion, venues)
                }
                .also { log.debug("Loaded remote data") }
        }
    }

}