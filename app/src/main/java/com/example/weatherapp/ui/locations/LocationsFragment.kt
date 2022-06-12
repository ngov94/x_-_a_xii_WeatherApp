package com.example.weatherapp.ui.locations


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.APIResponse.LocationWeather
import com.example.weatherapp.DataBase.FavLocations
import com.example.weatherapp.DataBase.WeatherDatabase
import com.example.weatherapp.R
import com.example.weatherapp.RetroApiInterface
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.limited_favorites_inflatable.view.*
import java.lang.Exception


class LocationsFragment : Fragment() {

    private var _binding: ActivityLocationsFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var placesClient: PlacesClient
    val locWeatherList = ArrayList<LocationWeather>()
    val adapter = LocationAdapter(locWeatherList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val intr = RetroApiInterface.create()
        val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
        val repo = WeatherRepository(intr, dao)
        val locationViewModel = LocationViewModel(repo)
        val googleApi = "AIzaSyAiANxOSE30Kd-izZbZ4PnYIGo6ROppsMs"
        val weatherApiKey = "863e72223d279e955d713a9437a9e6ce"
        var unit = "metric"

//        setFragmentResultListener("key_to_location"){key,result ->
//            //googleApi = result.getString("apikey").toString()
//            unit = result.getString("unit").toString()
//
//        }

        _binding = ActivityLocationsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Search to add location from list

        //<----Codes for Google autocomplete Fragment. More here: https://developers.google.cn/maps/documentation/places/android-sdk/autocomplete--->
        Places.initialize(context, googleApi)
        placesClient = Places.createClient(context)
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment_fav_location) as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                println("Fav Place: ${place.address}, ${place.latLng}")
                var placeName = place.address
                var latitude = place.latLng?.latitude.toString()
                var longitude = place.latLng?.longitude.toString()
                locationViewModel.insertFavLocation(FavLocations(placeName, latitude, longitude))

                Toast.makeText(context, "$placeName added to list", Toast.LENGTH_LONG).show()
            }

            override fun onError(status: Status) {
                println("An error occurred: $status")
            }
        })
//        <---End of Codes for Google autocomplete Fragment --->

        //Recycler view of location list
        locationViewModel.favLocationsList.observe(viewLifecycleOwner){
            locationViewModel.getFavLocationWeatherList(it, weatherApiKey, unit)
        }

        binding.favoritesRecycler.adapter = adapter
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(activity)

        locationViewModel.favLocationWeatherList.observe(viewLifecycleOwner){
            locWeatherList.clear()
            locWeatherList.addAll(it)
            adapter.notifyDataSetChanged()
        }

        //Delete Fav Location
        adapter.setOnItemLongClickListener(object : LocationAdapter.OnItemLongClickListener{
            override fun onItemLongClick(itemView: View) {
                var locId = itemView.fav_item_location_id.text.toString().toInt()
                var locName = itemView.fav_item_location.text.toString()
                var locLat = itemView.fav_item_location_lat.text.toString()
                var locLong = itemView.fav_item_location_long.text.toString()


            }

        })

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
