package com.example.restaurants.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VenueResponse(@JsonProperty("venues") val venues: List<VenueRemote>?)