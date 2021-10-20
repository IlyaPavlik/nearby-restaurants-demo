package com.example.restaurants.data.network.ext

import com.example.restaurants.data.network.model.VenueCategoryRemote
import com.example.restaurants.data.network.model.VenueLocationRemote
import com.example.restaurants.data.network.model.VenueRemote
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueCategory
import com.example.restaurants.domain.venue.model.VenueLocation

internal fun VenueCategory.toRemoteCategory(): VenueCategoryRemote = when (this) {
    VenueCategory.FOOD -> VenueCategoryRemote.FOOD
}

internal fun VenueRemote.toVenue() = Venue(
    id ?: "",
    name ?: "",
    location?.toVenueLocation() ?: EMPTY_LOCATION
)

private fun VenueLocationRemote.toVenueLocation() = VenueLocation(
    address ?: "",
    lat ?: 0.0,
    lng ?: 0.0,
    city ?: "",
    state ?: "",
    country ?: ""
)

private val EMPTY_LOCATION = VenueLocation(
    "",
    0.0,
    0.0,
    "",
    "",
    ""
)