package com.example.restaurants.data.network.converter

import com.example.restaurants.data.network.model.VenueLocationRemote
import com.example.restaurants.data.network.model.VenueRemote
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueLocation

class VenueConverter {

    fun fromVenueRemote(venueRemote: VenueRemote) = Venue(
        venueRemote.id ?: "",
        venueRemote.name ?: "",
        venueRemote.location?.let { fromVenueLocationRemote(it) } ?: EMPTY_LOCATION
    )

    private fun fromVenueLocationRemote(venueLocationRemote: VenueLocationRemote) = VenueLocation(
        venueLocationRemote.address ?: "",
        venueLocationRemote.lat ?: 0.0,
        venueLocationRemote.lng ?: 0.0,
        venueLocationRemote.city ?: "",
        venueLocationRemote.state ?: "",
        venueLocationRemote.country ?: ""
    )


    companion object {

        private val EMPTY_LOCATION = VenueLocation(
            "",
            0.0,
            0.0,
            "",
            "",
            ""
        )

    }

}