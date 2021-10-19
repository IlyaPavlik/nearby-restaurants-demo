package com.example.restaurants.domain.venue.repository

import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.model.VenuesRegion

interface VenueRepository {

    /**
     * Search venues on the server
     */
    suspend fun findRemoteVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue>

    /**
     * Search venues in cache
     */
    suspend fun findCachedVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue>

    /**
     * Save venues to cache
     * If passed venuesRegion contains cached regions they will be replaced by the new bigger region
     */
    suspend fun saveVenuesToCache(
        category: VenueCategory,
        venuesRegion: VenuesRegion,
        venues: List<Venue>
    )

}