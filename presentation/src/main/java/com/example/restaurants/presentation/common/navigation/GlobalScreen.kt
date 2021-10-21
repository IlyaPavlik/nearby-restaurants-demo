package com.example.restaurants.presentation.common.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen

internal class GlobalScreen {

    /**
     * App settings screen, see [android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS]
     */
    object Settings : ActivityScreen {
        override fun createIntent(context: Context): Intent =
            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", context.packageName, null)
            }
    }

}