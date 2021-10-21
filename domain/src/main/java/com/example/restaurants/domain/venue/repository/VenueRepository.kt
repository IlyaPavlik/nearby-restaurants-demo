package com.example.restaurants.domain.venue.repository

import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.model.VenuesRegion

interface VenueRepository {

    /**
     * Search venues on the server
     *
     * @param category category of the venue
     * @param venuesRegion visible region on the map
     * @return list of found venues
     */
    suspend fun findRemoteVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue>

    /**
     * Search venues in cache
     *
     * @param category category of the venue
     * @param venuesRegion visible region on the map
     * @return list of found venues
     */
    suspend fun findCachedVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue>

    /**
     * Save venues to cache
     * If passed venuesRegion contains cached regions they will be replaced by the new bigger region
     *
     * @param category category of the venue
     * @param venuesRegion visible region on the map
     * @param venues list of venues
     */
    suspend fun saveVenuesToCache(
        category: VenueCategory,
        venuesRegion: VenuesRegion,
        venues: List<Venue>
    )

}