package com.example.restaurants.presentation.main.pages.map

import androidx.lifecycle.ViewModel
import com.example.restaurants.domain.common.ext.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val log by logger("MapViewModel")


    init {
        log.debug("init")
    }

}