package com.example.restaurants.presentation.main.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.restaurants.presentation.main.pages.map.MapFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainScreen {

    object Map : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment =
            MapFragment.newInstance()
    }

}