package com.example.restaurants.data.repository

import com.example.restaurants.data.network.FoursquareService
import com.example.restaurants.data.network.converter.VenueConverter
import com.example.restaurants.data.network.model.VenueCategoryRemote
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.repository.VenueRepository
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val foursquareService: FoursquareService
) : VenueRepository {

    private val venueConverter by lazy { VenueConverter() }

    override suspend fun findVenues(
        category: VenueCategory,
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<Venue> {
        val categoryRemote = when (category) {
            VenueCategory.FOOD -> VenueCategoryRemote.FOOD
        }
        val latLong = LAT_LONG_PATTERN.format(latitude, longitude)

        return foursquareService.searchVenues(categoryRemote.id, latLong, radius)
            .let { response ->
                response.response.venues?.map { venueRemote ->
                    venueConverter.fromVenueRemote(venueRemote)
                } ?: emptyList()
            }
    }

    companion object {

        private const val LAT_LONG_PATTERN = "%f,%f"

    }

}