package com.example.weatherapp.ui.currentlocation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.example.weatherapp.databinding.FragmentCurrentLocationBinding
import com.example.weatherapp.ui.locations.LocationsViewModel

class CurrentLocationFragment : Fragment() {

    private var _binding: FragmentCurrentLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locationsViewModel =
            ViewModelProvider(this).get(LocationsViewModel::class.java)

        _binding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCurrentLocation
        locationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
