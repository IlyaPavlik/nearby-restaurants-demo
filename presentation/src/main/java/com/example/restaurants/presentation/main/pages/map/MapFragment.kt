package com.example.restaurants.presentation.main.pages.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.presentation.R
import com.example.restaurants.presentation.common.ext.observeOnce
import com.example.restaurants.presentation.databinding.FragmentMapBinding
import com.example.restaurants.presentation.main.pages.details.DetailsBottomSheet
import com.example.restaurants.presentation.main.pages.map.model.MapErrorType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val log by logger("MapFragment")

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private var map: GoogleMap? = null
    private val markers = HashMap<String, Marker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)
            ?.getMapAsync(this)

        viewModel.locationLiveData.observeOnce(viewLifecycleOwner, ::initCurrentLocation)
        viewModel.venusLiveData.observe(viewLifecycleOwner, ::showVenues)
        viewModel.errorToastLiveData.observe(viewLifecycleOwner, ::showMapError)
    }

    override fun onResume() {
        super.onResume()
        log.debug("onResume")
    }

    override fun onPause() {
        super.onPause()
        log.debug("onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        log.debug("onMapReady")
        this.map = map.apply {
            isMyLocationEnabled = true
            setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
        }
        map.setOnMarkerClickListener(this)
        map.setOnCameraIdleListener {
            viewModel.onVisibleRegionChanged(map.projection.visibleRegion)
        }
        viewModel.onMapReady()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val venue = marker.tag as Venue
        val bottomSheet = DetailsBottomSheet.newInstance(venue)
        bottomSheet.show(requireActivity().supportFragmentManager)
        return true
    }

    private fun initCurrentLocation(location: LocationLatLng) {
        log.debug("init current location: $location")
        val latLng = LatLng(location.latitude, location.longitude)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_CURRENT_LOCATION_ZOOM))
    }

    private fun showVenues(venues: List<Venue>) {
        val newVenueIds = HashSet<String>()

        // add new markers
        venues.forEach { venue ->
            newVenueIds.add(venue.id)
            if (!markers.containsKey(venue.id)) {
                val markerOptions = MarkerOptions()
                    .title(venue.name)
                    .position(LatLng(venue.location.latitude, venue.location.longitude))

                map?.addMarker(markerOptions)?.also { marker ->
                    marker.tag = venue
                    markers[venue.id] = marker
                }
            }
        }

        // remove old markers
        markers.keys.removeAll { id ->
            val needToRemove = !newVenueIds.contains(id)
            if (needToRemove) markers[id]?.remove()
            needToRemove
        }
    }

    private fun showMapError(error: MapErrorType) {
        showToast(getString(error.errorResId))
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val DEFAULT_CURRENT_LOCATION_ZOOM = 18F

        fun newInstance() = MapFragment()

    }
}