package com.example.restaurants.presentation.main

import androidx.lifecycle.ViewModel
import com.example.restaurants.domain.common.ext.logger
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

    init {
        log.debug("init")
        router.replaceScreen(MainScreen.Map)
    }

}