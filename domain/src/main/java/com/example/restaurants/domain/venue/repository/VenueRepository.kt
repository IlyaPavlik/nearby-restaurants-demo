package com.example.restaurants.domain.venue.repository

import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory

interface VenueRepository {

    suspend fun findVenues(
        category: VenueCategory,
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<Venue>

}