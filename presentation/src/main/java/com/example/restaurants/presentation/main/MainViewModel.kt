package com.example.restaurants.presentation.main

import androidx.lifecycle.ViewModel
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.presentation.common.navigation.GlobalScreen
import com.example.restaurants.presentation.common.navigation.NavigationManager
import com.example.restaurants.presentation.main.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigationManager: NavigationManager
) : ViewModel() {

    private val log by logger("MainViewModel")
    private val router = navigationManager.getRouter(MainScreen)
    private val globalRouter = navigationManager.getGlobalRouter()

    init {
        log.debug("init")
    }

    fun openMap() {
        router.replaceScreen(MainScreen.Map)
    }

    fun openAppSettings() {
        globalRouter.navigateTo(GlobalScreen.Settings)
    }

}