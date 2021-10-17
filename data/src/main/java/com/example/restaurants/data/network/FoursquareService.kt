package com.example.restaurants.data.network

import com.example.restaurants.data.network.model.FoursquareMetaResponse
import com.example.restaurants.data.network.model.VenueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareService {

    @GET("search")
    suspend fun searchVenues(
        @Query("categoryId") category: String,
        @Query("ll") latLong: String,
        @Query("radius") radius: Int,
    ): FoursquareMetaResponse<VenueResponse>

}