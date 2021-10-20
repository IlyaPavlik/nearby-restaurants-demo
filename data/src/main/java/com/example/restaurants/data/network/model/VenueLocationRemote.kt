package com.example.restaurants.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VenueLocationRemote(
    @JsonProperty("address") val address: String?,
    @JsonProperty("crossStreet") val crossStreet: String?,
    @JsonProperty("lat") val lat: Double?,
    @JsonProperty("lng") val lng: Double?,
    @JsonProperty("city") val city: String?,
    @JsonProperty("state") val state: String?,
    @JsonProperty("country") val country: String?
)