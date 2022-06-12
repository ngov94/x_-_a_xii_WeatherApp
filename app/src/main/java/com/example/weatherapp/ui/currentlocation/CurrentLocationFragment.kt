package com.example.weatherapp.ui.currentlocation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.DataBase.WeatherDatabase
import com.example.weatherapp.R
import com.example.weatherapp.RetroApiInterface
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.databinding.FragmentCurrentLocationBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.gson.GsonBuilder
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
        val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
        val repo = WeatherRepository(intr, dao)
        val currentLocationViewModel = WeatherViewModel(repo)

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



        //TODO : update current_degree_metric along with calculations
        //TODO : several gradients depending on weather
        //Geolocation is just lat and long.
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
//          println(pJson)
            //inserting date in shortened format

            binding.currentDate.text = SimpleDateFormat("MMM dd").format(Date()).toString()
            binding.currentTime.text = SimpleDateFormat("h:mm a").format(Date()).toString()

            binding.currentTemperature.text = it.current.temp.toString() + "°"
            binding.currentConditions.text = it.current.weather[0].main//.capitalize()


            binding.currentHumidity.text = it.current.humidity.toString() + "%"
            binding.currentDewPoint.text = it.current.dewPoint.toString() + "°"
            binding.currentPressure.text = it.current.pressure.toString() + " hPa"
            binding.currentUvIndex.text = it.current.uvi.toString()
            binding.currentVisibility.text = it.current.visibility.toString()+ "m"
            binding.currentMaxTemp.text = it.daily[0].temp.max.toString() + "°" //detail
            binding.currentMinTemp.text = it.daily[0].temp.min.toString() + "°" //detail
            binding.currentFeelsLike.text = it.current.feelsLike.toString() + "°" //detail


            //There are currently 5 hourly update textview // Please let me know if we need less or more
            //TODO : HourlyView binding = (hourly_icon_one, hourly_icon_two...) , (temp_one, temp_two, ...) , (time_one, time_two, ...)
            //Each view contained in HourlyView has a corresponding icon, temp, and time
            binding.hourlyIconOne.setImageDrawable(context?.getDrawable(setIcon(it.hourly[0].weather[0].icon)))
            binding.tempOne.text = it.hourly[0].temp.toString()
            binding.timeOne.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[0].dt.toLong()*1000))

            binding.hourlyIconTwo.setImageDrawable(context?.getDrawable(setIcon(it.hourly[1].weather[0].icon)))
            binding.tempTwo.text = it.hourly[1].temp.toString()
            binding.timeTwo.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[1].dt.toLong()*1000))

            binding.hourlyIconThree.setImageDrawable(context?.getDrawable(setIcon(it.hourly[2].weather[0].icon)))
            binding.tempThree.text = it.hourly[2].temp.toString()
            binding.timeThree.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[2].dt.toLong()*1000))

            binding.hourlyIconFour.setImageDrawable(context?.getDrawable(setIcon(it.hourly[3].weather[0].icon)))
            binding.tempFour.text = it.hourly[3].temp.toString()
            binding.timeFour.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[3].dt.toLong()*1000))

            binding.hourlyIconFive.setImageDrawable(context?.getDrawable(setIcon(it.hourly[4].weather[0].icon)))
            binding.tempFive.text = it.hourly[4].temp.toString()
            binding.timeFive.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[4].dt.toLong()*1000))


            //TODO : Please inflate the alert view (setVisibility(VISIBLE)) should there be an alert on update



            setFragmentResult("key_to_weekly", bundleOf("daily" to it.daily))
            //Sending google api and unit to location fragment
            setFragmentResult("key_to_location", bundleOf("apikey" to googleApi, "unit" to units))
        }

        currentLocationViewModel.currentCity.observe(viewLifecycleOwner) {
            println(it)
            var city = it.results[0].components.city
            if (city == null) city = it.results[0].components.county
            var state = it.results[0].components.state
            var country = it.results[0].components.country
            var placeName = "$city, $state, $country"
            //binding.tvCityName.text = placeName TODO : Please insert this into the searchbar // searchViewID.setQuery(searchToken, false);
        }


        //changes metric when view is clicked
        //binding.metric_change_button.setOnClickListener {} TODO : Please set an on-click listener to change the metric (Fahrenheit - Celcius)


        return root
    }

    fun setIcon(icon: String): Int{
        var iconNumber = when (icon){
            "01d" -> R.drawable.w_clear_sky_day
            "01n" -> R.drawable.w_clear_sky_night
            "02d" -> R.drawable.w_few_clouds_day
            "02n" -> R.drawable.w_few_clouds_night
            "03d" -> R.drawable.w_scattered_clouds
            "03n" -> R.drawable.w_scattered_clouds
            "04d" -> R.drawable.w_broken_clouds
            "04n" -> R.drawable.w_broken_clouds
            "09d" -> R.drawable.w_shower_rain
            "09n" -> R.drawable.w_shower_rain
            "10d" -> R.drawable.w_rain_day
            "10n" -> R.drawable.w_rain_night
            "11d" -> R.drawable.w_thunderstorm
            "11n" -> R.drawable.w_thunderstorm
            "13d" -> R.drawable.w_snow
            "13n" -> R.drawable.w_snow
            "50d" -> R.drawable.w_mist
            "50n" -> R.drawable.w_mist
            else -> R.drawable.w_clear_sky_day
        }
        return iconNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
