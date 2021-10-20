package com.example.restaurants.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VenueRemote(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("location") val location: VenueLocationRemote?
)