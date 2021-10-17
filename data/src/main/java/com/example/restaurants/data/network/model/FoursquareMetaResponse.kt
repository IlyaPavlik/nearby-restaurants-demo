package com.example.restaurants.data.network.model

data class FoursquareMetaResponse<Response>(
    val meta: Meta,
    val response: Response
) {

    data class Meta(
        val code: String?,
        val requestId: String?
    )
}