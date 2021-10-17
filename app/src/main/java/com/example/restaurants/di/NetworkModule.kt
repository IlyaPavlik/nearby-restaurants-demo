package com.example.restaurants.di

import com.example.restaurants.data.network.FoursquareService
import com.example.restaurants.data.network.FoursquareServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFoursquareService(): FoursquareService =
        FoursquareServiceProvider().provideFoursquareService()

}