package com.example.restaurants.presentation.main.pages.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.restaurants.domain.venue.model.Venue
import com.example.restaurants.presentation.databinding.BottomSheetDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = requireArguments().getString(NAME_KEY)
        binding.city.text = requireArguments().getString(CITY_KEY)
        binding.address.text = requireArguments().getString(ADDRESS_KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    companion object {

        private const val TAG = "DetailsBottomSheet"
        private const val NAME_KEY = "NAME_KEY"
        private const val CITY_KEY = "CITY_KEY"
        private const val ADDRESS_KEY = "ADDRESS_KEY"

        fun newInstance(venue: Venue) = DetailsBottomSheet().apply {
            arguments = Bundle().apply {
                putString(NAME_KEY, venue.name)
                putString(CITY_KEY, venue.location.city)
                putString(ADDRESS_KEY, venue.location.address)
            }
        }

    }
}