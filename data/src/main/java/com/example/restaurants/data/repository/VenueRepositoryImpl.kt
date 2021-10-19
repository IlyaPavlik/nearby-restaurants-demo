package com.example.restaurants.data.repository

import com.example.restaurants.data.network.FoursquareService
import com.example.restaurants.data.network.ext.toRemoteCategory
import com.example.restaurants.data.network.ext.toVenue
import com.example.restaurants.data.network.model.VenueCategoryRemote
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.model.VenuesRegion
import com.example.restaurants.domain.venue.repository.VenueRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class VenueRepositoryImpl @Inject constructor(
    private val foursquareService: FoursquareService
) : VenueRepository {

    private val cache = HashMap<VenueCategory, MutableMap<VenuesRegion, List<Venue>>>()

    override suspend fun findRemoteVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue> {
        val categoryRemote = category.toRemoteCategory()
        val latLong = LAT_LONG_PATTERN.format(
            venuesRegion.center.latitude,
            venuesRegion.center.longitude
        )
        val radius = venuesRegion.radius.roundToInt()

        return searchRemoteVenues(categoryRemote, latLong, radius)
    }

    override suspend fun findCachedVenues(
        category: VenueCategory,
        venuesRegion: VenuesRegion
    ): List<Venue> {
        cache[category]?.forEach { (region, venues) ->
            if (region.contains(venuesRegion)) {
                return venues.filterByRegion(venuesRegion)
            }
        }
        return emptyList()
    }

    override suspend fun saveVenuesToCache(
        category: VenueCategory,
        venuesRegion: VenuesRegion,
        venues: List<Venue>
    ) {
        val categoryCache = cache[category] ?: HashMap()
        val smallerRegions = categoryCache.keys.filter { region -> venuesRegion.contains(region) }

        categoryCache.keys.removeAll(smallerRegions)

        categoryCache[venuesRegion] = venues

        cache[category] = categoryCache
    }

    private suspend fun searchRemoteVenues(
        categoryRemote: VenueCategoryRemote,
        latLong: String,
        radius: Int
    ): List<Venue> {
        return foursquareService.searchVenues(categoryRemote.id, latLong, radius)
            .let { response ->
                response.response.venues
                    ?.map { venueRemote -> venueRemote.toVenue() }
                    ?: emptyList()
            }
    }

    private fun List<Venue>.filterByRegion(venuesRegion: VenuesRegion): List<Venue> =
        filter { venue ->
            venuesRegion.contains(
                LocationLatLng(
                    venue.location.latitude,
                    venue.location.longitude
                )
            )
        }

    companion object {

        private const val LAT_LONG_PATTERN = "%f,%f"

    }

}