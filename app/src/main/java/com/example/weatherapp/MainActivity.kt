package com.example.weatherapp


import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    lateinit var vm : WeatherViewModel

    //---------------
    private lateinit var binding: ActivityMainBinding
//--------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val googleApi = "AIzaSyAiANxOSE30Kd-izZbZ4PnYIGo6ROppsMs" // Google Cloud API
//        val weatherApiKey = "863e72223d279e955d713a9437a9e6ce"    // Open Weather API
//        val openCageDataKey = "8eb888cd6f6142ee9203998161b2eb7c"  // OpenCage Geocoding API
//
//        val intr = RetroApiInterface.create()
//        val repo = WeatherRepository(intr)
//        vm = WeatherViewModel(repo)
//
//        var units = "metric"  //imperial
//
//        vm.getGeoloaction(googleApi)

        //----------------------------
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.locationsFragment2, R.id.weeklyFragment2, R.id.mapFragment2
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//--------------------------
    }
}




//        setContentView(R.layout.activity_main)

//        vm.currentLocation.observe(this){ it ->
////            println(it)
//            var latitude = it.location.lat.toString()
//            var longitude = it.location.lng.toString()
//            vm.getCurrentCity("$latitude+$longitude", openCageDataKey)
//            vm.getCurrentWeather(latitude, longitude, weatherApiKey, units)
//        }
//
////<----Codes for Google autocomplete Fragment. More here: https://developers.google.cn/maps/documentation/places/android-sdk/autocomplete--->
//        Places.initialize(applicationContext, googleApi)
//        placesClient = Places.createClient(this)
//        // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//                    as AutocompleteSupportFragment
//
//        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                // TODO: Get info about the selected place.
//                println("Place: ${place.address}, ${place.latLng}")
//                tv_city_name.text = place.address
//                var latitude = place.latLng?.latitude.toString()
//                var longitude = place.latLng?.longitude.toString()
//                vm.getCurrentWeather(latitude, longitude, weatherApiKey, units)
//            }
//
//            override fun onError(status: Status) {
//                println("An error occurred: $status")
//            }
//        })
////        <---End of Codes for Google autocomplete Fragment --->
//
//
//
//        vm.currentWeather.observe(this) {
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            val pJson = gson.toJson(it)

//        vm.currentCity.observe(this){
//            println(it)
//            var city = it.results[0].components.city
//            if(city == null) city = it.results[0].components.county
//            var state = it.results[0].components.state
//            var country = it.results[0].components.country
//            var placeName = "$city, $state, $country"
//            tv_city_name.text = placeName
//        }


//--------------------------
