package com.example.restaurants

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NearRestaurantsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            System.setProperty("kotlinx.coroutines.debug", "on")
        }
    }
}