package com.example.restaurants.domain.venue

import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.domain.venue.model.VenueLocation
import com.example.restaurants.domain.venue.model.VenuesRegion
import com.example.restaurants.domain.venue.repository.VenueRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class VenueInteractorTest {

    private val venueRepository = mock<VenueRepository>()
    private val venueInteractor = VenueInteractor(venueRepository)

    @Test
    fun venueInteractor_SearchFoodVenues_Remote() = runBlockingTest {
        `when`(venueRepository.findRemoteVenues(any(), any())).thenReturn(MOCK_VENUES)
        `when`(venueRepository.findCachedVenues(any(), any())).thenReturn(emptyList())

        val venues = venueInteractor.searchFoodVenues(MOCK_VENUES_REGION)

        verify(venueRepository).findCachedVenues(any(), any())
        verify(venueRepository).findRemoteVenues(any(), any())
        verify(venueRepository).saveVenuesToCache(any(), any(), any())

        Assert.assertEquals(venues, MOCK_VENUES)
    }

    @Test
    fun venueInteractor_SearchFoodVenues_Cached() = runBlockingTest {
        `when`(venueRepository.findCachedVenues(any(), any())).thenReturn(MOCK_VENUES)

        val venues = venueInteractor.searchFoodVenues(MOCK_VENUES_REGION)

        verify(venueRepository).findCachedVenues(any(), any())

        Assert.assertEquals(venues, MOCK_VENUES)
    }

    companion object {

        private val MOCK_VENUES_REGION = VenuesRegion(
            southwest = LocationLatLng(0.0, 0.0),
            northeast = LocationLatLng(0.0, 0.0),
            center = LocationLatLng(0.0, 0.0),
            radius = 0F
        )

        private val MOCK_VENUES = listOf(
            Venue("1", "name 1", VenueLocation("", 0.0, 0.0, "", "", "")),
            Venue("2", "name 2", VenueLocation("", 0.0, 0.0, "", "", ""))
        )

    }
}