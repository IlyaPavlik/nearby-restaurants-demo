package com.example.restaurants.di

import com.example.restaurants.data.repository.LocationRepositoryImpl
import com.example.restaurants.data.repository.VenueRepositoryImpl
import com.example.restaurants.domain.location.repository.LocationRepository
import com.example.restaurants.domain.venue.repository.VenueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindVenueRepository(repository: VenueRepositoryImpl): VenueRepository

}