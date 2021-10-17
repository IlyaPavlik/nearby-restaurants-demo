package com.example.restaurants.presentation.common.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationManager @Inject constructor() {

    private val localCiceroneMap = HashMap<String, Cicerone<Router>>()

    fun getRouter(tag: Any): Router = getCicerone("$tag").router

    fun getNavigationHolder(tag: Any): NavigatorHolder = getCicerone("$tag").getNavigatorHolder()

    private fun getCicerone(tag: String): Cicerone<Router> {
        synchronized(localCiceroneMap) {
            if (localCiceroneMap.containsKey(tag).not()) {
                localCiceroneMap[tag] = Cicerone.create(Router())
            }
            return localCiceroneMap.getValue(tag)
        }
    }

}