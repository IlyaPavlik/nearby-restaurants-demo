package com.example.restaurants.presentation.main.pages.map

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.restaurants.domain.common.ext.logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val log by logger("MapFragment")

    private val viewModel: MapViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        log.debug("onResume")
    }

    override fun onPause() {
        super.onPause()
        log.debug("onPause")
    }

    companion object {

        fun newInstance() = MapFragment()

    }
}