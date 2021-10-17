package com.example.restaurants.presentation.main.pages.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.domain.location.model.LocationLatLng
import com.example.restaurants.presentation.R
import com.example.restaurants.presentation.common.ext.observeOnce
import com.example.restaurants.presentation.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private val log by logger("MapFragment")

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private var map: GoogleMap? = null

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
        map.setOnMyLocationButtonClickListener(this)
        viewModel.onMapReady()
    }

    override fun onMyLocationButtonClick(): Boolean {
        log.debug("onMyLocationButtonClick")
        return false
    }

    private fun initCurrentLocation(location: LocationLatLng) {
        log.debug("init current location: $location")
        val latLng = LatLng(location.latitude, location.longitude)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_CURRENT_LOCATION_ZOOM))
    }

    companion object {

        private const val DEFAULT_CURRENT_LOCATION_ZOOM = 15F

        fun newInstance() = MapFragment()

    }
}