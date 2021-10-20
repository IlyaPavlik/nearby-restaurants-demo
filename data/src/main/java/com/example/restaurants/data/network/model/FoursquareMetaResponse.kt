package com.example.restaurants.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FoursquareMetaResponse<Response>(
    @JsonProperty("meta") val meta: Meta,
    @JsonProperty("response") val response: Response
) {

    data class Meta(
        @JsonProperty("code") val code: String?,
        @JsonProperty("requestId") val requestId: String?
    )
}