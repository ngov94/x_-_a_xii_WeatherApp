package com.example.weatherapp.ui.currentlocation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.weatherapp.R
import com.example.weatherapp.RetroApiInterface
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.example.weatherapp.databinding.FragmentCurrentLocationBinding
import com.example.weatherapp.ui.locations.LocationsViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_current_location.*
import java.text.SimpleDateFormat
import java.util.*

class CurrentLocationFragment : Fragment() {

    private var _binding: FragmentCurrentLocationBinding? = null
    private val binding get() = _binding!!

    lateinit var placesClient: PlacesClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val intr = RetroApiInterface.create()
        val repo = WeatherRepository(intr)
        val currentLocationViewModel = ViewModelProvider(this).get(CurrentLocationViewModel(repo)::class.java)

        _binding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textCurrentLocation
//        currentLocationViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val googleApi = "AIzaSyAiANxOSE30Kd-izZbZ4PnYIGo6ROppsMs" // Google Cloud API
        val weatherApiKey = "863e72223d279e955d713a9437a9e6ce"    // Open Weather API
        val openCageDataKey = "8eb888cd6f6142ee9203998161b2eb7c"  // OpenCage Geocoding API



        var units = "metric"  //imperial

        currentLocationViewModel.getGeoloaction(googleApi)

        currentLocationViewModel.currentLocation.observe(viewLifecycleOwner){ it ->
//            println(it)
            var latitude = it.location.lat.toString()
            var longitude = it.location.lng.toString()
            currentLocationViewModel.getCurrentCity("$latitude+$longitude", openCageDataKey)
            currentLocationViewModel.getCurrentWeather(latitude, longitude, weatherApiKey, units)
        }

//<----Codes for Google autocomplete Fragment. More here: https://developers.google.cn/maps/documentation/places/android-sdk/autocomplete--->
        Places.initialize(context, googleApi)
        placesClient = Places.createClient(context)
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = autocomplete_fragment as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                println("Place: ${place.address}, ${place.latLng}")
                tv_city_name.text = place.address
                var latitude = place.latLng?.latitude.toString()
                var longitude = place.latLng?.longitude.toString()
                currentLocationViewModel.getCurrentWeather(latitude, longitude, weatherApiKey, units)
            }

            override fun onError(status: Status) {
                println("An error occurred: $status")
            }
        })
//        <---End of Codes for Google autocomplete Fragment --->



        currentLocationViewModel.currentWeather.observe(viewLifecycleOwner) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val pJson = gson.toJson(it)
//            println(pJson)
            tv_date_and_time.text = SimpleDateFormat("dd MMMM yyyy hh:mm a").format(Date())
            tv_day_max_temp.text = "Max " + it.daily[0].temp.max.toString() + "º"
            tv_day_min_temp.text = "Min " + it.daily[0].temp.min.toString() + "º"
            tv_current_temp.text = it.current.temp.toString() + "º"
            tv_feels_like.text = "Feels like " + it.current.feelsLike.toString() + "º"
            tv_weather_type.text = it.current.weather[0].description.capitalize()
        }

        currentLocationViewModel.currentCity.observe(viewLifecycleOwner) {
            println(it)
            var city = it.results[0].components.city
            if (city == null) city = it.results[0].components.county
            var state = it.results[0].components.state
            var country = it.results[0].components.country
            var placeName = "$city, $state, $country"
            tv_city_name.text = placeName


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
