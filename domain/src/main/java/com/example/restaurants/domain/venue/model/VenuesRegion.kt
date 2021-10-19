package com.example.restaurants.domain.venue.model

import com.example.restaurants.domain.location.model.LocationLatLng

data class VenuesRegion(
    val southwest: LocationLatLng,
    val northeast: LocationLatLng,
    val center: LocationLatLng,
    val radius: Float
) {

    fun contains(region: VenuesRegion): Boolean {
        return contains(region.southwest) && contains(region.northeast)
    }

    fun contains(point: LocationLatLng): Boolean {
        return point.latitude in southwest.latitude..northeast.latitude
                && containsLongitude(point.longitude)
    }

    private fun containsLongitude(pointLongitude: Double): Boolean {
        val southwestLongitude = southwest.longitude
        val northeastLongitude = northeast.longitude

        return if (southwestLongitude <= northeastLongitude) {
            pointLongitude in southwestLongitude..northeastLongitude
        } else {
            southwestLongitude <= pointLongitude || pointLongitude <= northeastLongitude
        }
    }

}
