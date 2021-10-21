package com.example.restaurants.presentation.common.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Navigation manager manages navigation holders for different screen chains.
 * https://github.com/terrakok/Cicerone
 */
class NavigationManager @Inject constructor() {

    private val globalCicerone = Cicerone.create(Router())
    private val localCiceroneMap = HashMap<String, Cicerone<Router>>()

    fun getGlobalRouter(): Router = globalCicerone.router

    fun getRouter(tag: Any): Router = getCicerone("$tag").router

    fun getGlobalNavigationHolder(): NavigatorHolder = globalCicerone.getNavigatorHolder()

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