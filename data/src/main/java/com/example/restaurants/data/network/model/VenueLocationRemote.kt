package com.example.restaurants.data.network.model

data class VenueLocationRemote(
    val address: String?,
    val crossStreet: String?,
    val lat: Double?,
    val lng: Double?,
    val city: String?,
    val state: String?,
    val country: String?
)