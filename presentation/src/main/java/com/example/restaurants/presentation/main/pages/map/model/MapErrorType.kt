package com.example.restaurants.presentation.main.pages.map.model

import androidx.annotation.StringRes
import com.example.restaurants.presentation.R

enum class MapErrorType(@StringRes val errorResId: Int) {
    LOCATION(R.string.error_location),
    VENUES(R.string.error_venues)
}