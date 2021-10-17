package com.example.restaurants.domain.venue.model

data class VenueLocation(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String
)