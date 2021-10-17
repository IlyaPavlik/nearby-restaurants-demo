package com.example.restaurants.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.presentation.R
import com.example.restaurants.presentation.common.navigation.NavigationManager
import com.example.restaurants.presentation.databinding.ActivityMainBinding
import com.example.restaurants.presentation.main.navigation.MainScreen
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val log by logger("MainActivity")

    private lateinit var binding: ActivityMainBinding

    private val navigator = AppNavigator(this, R.id.content)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        log.debug("onResumeFragments")
        navigationManager.getNavigationHolder(MainScreen).setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        log.debug("onPause")
        navigationManager.getNavigationHolder(MainScreen).removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        log.debug("onDestroy, finishing: $isFinishing")
    }
}