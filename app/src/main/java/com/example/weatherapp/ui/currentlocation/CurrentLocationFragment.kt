package com.example.weatherapp.ui.currentlocation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.weatherapp.*
import com.example.weatherapp.data.DataBase.WeatherDatabase
import com.example.weatherapp.data.RetroApiInterface
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.databinding.FragmentCurrentLocationBinding
import com.example.weatherapp.log.DebugTree
import com.example.weatherapp.ui.WeatherViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_current_location.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class CurrentLocationFragment : Fragment() {

    private var _binding: FragmentCurrentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var placesClient: PlacesClient
    private lateinit var currentLocationViewModel: WeatherViewModel

    private val googleApi = BuildConfig.GOOGLE_KEY // Google Cloud API
    private val weatherApiKey = BuildConfig.WEATHER_KEY
    private val openCageDataKey = BuildConfig.OPENCAGE_KEY
    var units = "metric"  //imperial
    var latitude = ""
    var longitude = ""

    lateinit  var autocompleteFragment: AutocompleteSupportFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.plant(DebugTree())

        setHasOptionsMenu(true)

        try {
            val intr = RetroApiInterface.create()
            val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
            val repo = WeatherRepository(intr, dao)
            currentLocationViewModel = WeatherViewModel(repo)
        }catch (e: Exception){
            Timber.e("Exception Caught: $e")
        }

        _binding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        currentLocationViewModel.getGeoloaction(googleApi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext={
                    latitude = it.location.lat.toString()
                    longitude = it.location.lng.toString()
                    getCurrentWeather(latitude, longitude)
                    getCurrentCity(latitude, longitude)
                },
                onError = {e -> println("this is error $e")}
            )

//<----Codes for Google autocomplete Fragment. More here: https://developers.google.cn/maps/documentation/places/android-sdk/autocomplete--->
        Places.initialize(requireContext(), googleApi)
        placesClient = Places.createClient(requireContext())
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                latitude = place.latLng?.latitude.toString()
                longitude = place.latLng?.longitude.toString()
                getCurrentWeather(latitude, longitude)
                autocompleteFragment.setHint(place.address)
            }

            override fun onError(status: Status) {
                println("An error occurred: $status")
            }
        })
//        <---End of Codes for Google autocomplete Fragment --->
        return root
    }

    private fun setIcon(icon: String): Int{
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

    fun getCurrentWeather(latitude: String, longitude:String){
        currentLocationViewModel.getCurrentWeather(latitude, longitude, weatherApiKey, units)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext ={

                    binding.currentDate.text = SimpleDateFormat("MMM dd").format(it.current.dt.toLong()*1000).toString()
                    binding.currentTime.text = SimpleDateFormat("h:mm a").format(it.current.dt.toLong()*1000).toString()

                    binding.currentTemperature.text = it.current.temp.roundToInt().toString() + "°"
                    binding.currentConditions.text = it.current.weather[0].main//.capitalize()
                    binding.currentIcon.setImageDrawable(context?.getDrawable(setIcon(it.current.weather[0].icon)))


                    binding.currentHumidity.text = it.current.humidity.toString() + "%"
                    binding.currentDewPoint.text = it.current.dewPoint.roundToInt().toString() + "°"
                    binding.currentPressure.text = it.current.pressure.toString() + " hPa"
                    binding.currentUvIndex.text = it.current.uvi.toString()
                    binding.currentVisibility.text = it.current.visibility.toString()+ "m"
                    binding.currentMaxTemp.text = it.daily[0].temp.max.roundToInt().toString() + "°" //detail
                    binding.currentMinTemp.text = it.daily[0].temp.min.roundToInt().toString() + "°" //detail
                    binding.currentFeelsLike.text = it.current.feelsLike.roundToInt().toString() + "°" //detail


                    //5 hourly update textview
                    binding.hourlyIconOne.setImageDrawable(context?.getDrawable(setIcon(it.hourly[1].weather[0].icon)))
                    binding.tempOne.text = it.hourly[1].temp.roundToInt().toString()
                    binding.timeOne.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[1].dt.toLong()*1000))

                    binding.hourlyIconTwo.setImageDrawable(context?.getDrawable(setIcon(it.hourly[2].weather[0].icon)))
                    binding.tempTwo.text = it.hourly[2].temp.roundToInt().toString()
                    binding.timeTwo.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[2].dt.toLong()*1000))

                    binding.hourlyIconThree.setImageDrawable(context?.getDrawable(setIcon(it.hourly[3].weather[0].icon)))
                    binding.tempThree.text = it.hourly[3].temp.roundToInt().toString()
                    binding.timeThree.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[3].dt.toLong()*1000))

                    binding.hourlyIconFour.setImageDrawable(context?.getDrawable(setIcon(it.hourly[4].weather[0].icon)))
                    binding.tempFour.text = it.hourly[4].temp.roundToInt().toString()
                    binding.timeFour.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[4].dt.toLong()*1000))

                    binding.hourlyIconFive.setImageDrawable(context?.getDrawable(setIcon(it.hourly[5].weather[0].icon)))
                    binding.tempFive.text = it.hourly[5].temp.roundToInt().toString()
                    binding.timeFive.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[5].dt.toLong()*1000))

                    binding.hourlyIconSix.setImageDrawable(context?.getDrawable(setIcon(it.hourly[6].weather[0].icon)))
                    binding.tempSix.text = it.hourly[6].temp.roundToInt().toString()
                    binding.timeSix.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[6].dt.toLong()*1000))

                    binding.hourlyIconSeven.setImageDrawable(context?.getDrawable(setIcon(it.hourly[7].weather[0].icon)))
                    binding.tempSeven.text = it.hourly[7].temp.roundToInt().toString()
                    binding.timeSeven.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[7].dt.toLong()*1000))

                    binding.hourlyIconEight.setImageDrawable(context?.getDrawable(setIcon(it.hourly[8].weather[0].icon)))
                    binding.tempEight.text = it.hourly[8].temp.roundToInt().toString()
                    binding.timeSeven.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[8].dt.toLong()*1000))

                    binding.hourlyIconNine.setImageDrawable(context?.getDrawable(setIcon(it.hourly[9].weather[0].icon)))
                    binding.tempNine.text = it.hourly[9].temp.roundToInt().toString()
                    binding.timeNine.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[9].dt.toLong()*1000))

                    binding.hourlyIconTen.setImageDrawable(context?.getDrawable(setIcon(it.hourly[10].weather[0].icon)))
                    binding.tempNine.text = it.hourly[10].temp.roundToInt().toString()
                    binding.timeNine.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[10].dt.toLong()*1000))

                    binding.hourlyIconEleven.setImageDrawable(context?.getDrawable(setIcon(it.hourly[11].weather[0].icon)))
                    binding.tempEleven.text = it.hourly[11].temp.roundToInt().toString()
                    binding.timeEleven.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[11].dt.toLong()*1000))

                    binding.hourlyIconTwelve.setImageDrawable(context?.getDrawable(setIcon(it.hourly[12].weather[0].icon)))
                    binding.tempTwelve.text = it.hourly[12].temp.roundToInt().toString()
                    binding.timeTwelve.text = SimpleDateFormat("h:mm a").format(Date(it.hourly[12].dt.toLong()*1000))



                    //alerts
                    if (it.alerts != null){
                        binding.alertSection.visibility = View.VISIBLE
                        binding.alertEvent.text = it.alerts[0].event.uppercase()
                        binding.alertDescrip.text = it.alerts[0].description
                        binding.alertSender.text = it.alerts[0].senderName

                    }else{
                        binding.alertSection.visibility = View.GONE
                    }

                    setFragmentResult("key_to_weekly", bundleOf("daily" to it.daily))},
                onError = {e -> println("this is error $e")}
            )
    }

    private fun getCurrentCity(latitude: String, longitude:String){
        currentLocationViewModel.getCurrentCity("$latitude+$longitude", openCageDataKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext={
                    var city = it.results[0].components.city
                    if (city == null) city = it.results[0].components.county
                    var state = it.results[0].components.state
                    var country = it.results[0].components.country
                    var placeName = "$city, $state, $country"
                    autocompleteFragment.setHint(placeName)
                },
                onError = {e -> println("this is error $e")}
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.action_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.degreeUnit -> {
                if (units == "metric") {
                    units = "imperial"
                    item.setIcon(R.drawable.unit_imperial)
                    item.setTitle("Imperial Units")

                } else {
                    units = "metric"
                    item.setIcon(R.drawable.unit_metric)
                    item.setTitle("Metric Units")
                }
                getCurrentWeather(latitude,longitude)

                super.onOptionsItemSelected(item)
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}