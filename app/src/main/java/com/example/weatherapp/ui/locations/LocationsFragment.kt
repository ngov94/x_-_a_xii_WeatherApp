package com.example.weatherapp.ui.locations


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class LocationsFragment : Fragment() {

    private var _binding: ActivityLocationsFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ActivityLocationsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val googleApi = "AIzaSyAiANxOSE30Kd-izZbZ4PnYIGo6ROppsMs"


        /**TODO : There's a SearchView users can use to add search for locations,but please feel free to remove
        if you've got a better idea**/

        //<----Codes for Google autocomplete Fragment. More here: https://developers.google.cn/maps/documentation/places/android-sdk/autocomplete--->
        Places.initialize(context, googleApi)
        placesClient = Places.createClient(context)
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                println("Place: ${place.address}, ${place.latLng}")
                //binding.tvCityName.text = place.address TODO : Please insert this text into the searchbar // searchViewID.setQuery(searchToken, false);
                var latitude = place.latLng?.latitude.toString()
                var longitude = place.latLng?.longitude.toString()
                // TODO: change this
                //currentLocationViewModel.getCurrentWeather(latitude, longitude, weatherApiKey, units)
            }

            override fun onError(status: Status) {
                println("An error occurred: $status")
            }
        })
//        <---End of Codes for Google autocomplete Fragment --->




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//    lateinit var vm : WeatherViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //setContentView(R.layout.activity_locations_fragment)
//
//
//        val intr = RetroApiInterface.create()
//        val repo = WeatherRepository(intr)
//        vm = WeatherViewModel(repo)
//
//        vm.currentWeather.observe(this) {
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            val pJson = gson.toJson(it)
//            println(pJson)
//
//            tv_city_name.text = it.name
//            tv_date_and_time.text = SimpleDateFormat("dd/M/yyyy hh:mm a").format(Date())
//            tv_day_max_temp.text = "Day " + it.main.tempMax.toString()+"º"
//            tv_day_min_temp.text = "Night " +it.main.tempMin.toString()+"º"
//            tv_current_temp.text = it.main.temp.toString()+"º"
//            tv_feels_like.text = "Feels like "+ it.main.feelsLike.toString()+"º"
//            tv_weather_type.text = it.weather[0].description.capitalize()
//        }
//
//        var latitude = "40.71427"
//        var longitude = "-74.00597"
//        var apiKey = "2969a813a03aab47497e1da3a8cf1a81"
//        var String = "metric"  //imperial
//
//        vm.getCurrentWeather(latitude,longitude,apiKey,String)
//    }
