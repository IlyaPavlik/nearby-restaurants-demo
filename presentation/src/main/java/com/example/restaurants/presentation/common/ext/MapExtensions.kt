package com.example.restaurants.presentation.common.ext

import android.location.Location
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.venue.model.VenuesRegion
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion

fun VisibleRegion.toVenuesRegion() = VenuesRegion(
    southwest = latLngBounds.southwest.toLocationLatLng(),
    northeast = latLngBounds.northeast.toLocationLatLng(),
    center = latLngBounds.center.toLocationLatLng(),
    radius = getVisibleRadius()
)

fun LatLng.toLocationLatLng() = LocationLatLng(latitude, longitude)

fun VisibleRegion.getVisibleRadius(): Float {
    val distanceWidth = FloatArray(1)
    //calculate the distance between left <-> right of map on screen
    Location.distanceBetween(
        (farLeft.latitude + nearLeft.latitude) / 2,
        farLeft.longitude,
        (farRight.latitude + nearRight.latitude) / 2,
        farRight.longitude,
        distanceWidth
    )
    return distanceWidth[0] / 2F
}