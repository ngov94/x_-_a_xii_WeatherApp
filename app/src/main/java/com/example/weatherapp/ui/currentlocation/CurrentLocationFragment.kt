package com.example.weatherapp.ui.currentlocation

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapp.*
import com.example.weatherapp.DataBase.WeatherDatabase
import com.example.weatherapp.databinding.FragmentCurrentLocationBinding
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class CurrentLocationFragment : Fragment() {

    private var _binding: FragmentCurrentLocationBinding? = null
    private val binding get() = _binding!!

    lateinit var placesClient: PlacesClient
    lateinit var currentLocationViewModel: WeatherViewModel

    private val googleApi = BuildConfig.GOOGLE_KEY // Google Cloud API
    private val weatherApiKey = BuildConfig.WEATHER_KEY
    private val openCageDataKey = BuildConfig.OPENCAGE_KEY
    private var units = "metric"  //imperial

    lateinit var autocompleteFragment: AutocompleteSupportFragment
    lateinit var currentW: TextView
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        val intr = RetroApiInterface.create()
        val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
        val repo = WeatherRepository(intr, dao)
        currentLocationViewModel = WeatherViewModel(repo)

        _binding = FragmentCurrentLocationBinding.inflate(inflater, container, false)

        var latitude = ""
        var longitude = ""

        root = binding.root

        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        //Geolocation is just lat and long.
        currentLocationViewModel.getGeoloaction(googleApi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    latitude = it.location.lat.toString()
                    longitude = it.location.lng.toString()
                    getCurrentWeather(latitude, longitude)
                    getCurrentCity(latitude, longitude)
                },
                onError = { e -> println("this is error $e") }
            )

        binding.settings.setOnClickListener {
            if (units == "metric") {
                units = "imperial"
                getCurrentWeather(latitude, longitude)
            } else {
                units = "metric"
                getCurrentWeather(latitude, longitude)
            }


        }
//<----Codes for Google autocomplete Fragment --->
        Places.initialize(requireContext(), googleApi)
        placesClient = Places.createClient(requireContext())
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS))
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

    fun setIcon(icon: String): Int {
        var iconNumber = when (icon) {
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

    fun getCurrentWeather(latitude: String, longitude: String) {
        currentLocationViewModel.getCurrentWeather(latitude, longitude, weatherApiKey, units)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {

                    binding.currentDate.text =
                        SimpleDateFormat("MMM dd").format(it.current.dt.toLong() * 1000).toString()
                    binding.currentTime.text =
                        SimpleDateFormat("h:mm a").format(it.current.dt.toLong() * 1000).toString()

                    binding.currentTemperature.text = it.current.temp.roundToInt().toString() + "°"

                    binding.currentConditions.text = it.current.weather[0].main//.capitalize()
                    binding.currentIcon.setImageDrawable(context?.getDrawable(setIcon(it.current.weather[0].icon)))


                    binding.currentHumidity.text = it.current.humidity.toString() + "%"
                    binding.currentDewPoint.text = it.current.dewPoint.roundToInt().toString() + "°"
                    binding.currentPressure.text = it.current.pressure.toString() + " hPa"
                    binding.currentUvIndex.text = it.current.uvi.toString()
                    binding.currentVisibility.text = it.current.visibility.toString() + "m"
                    binding.currentMaxTemp.text =
                        it.daily[0].temp.max.roundToInt().toString() + "°" //detail
                    binding.currentMinTemp.text =
                        it.daily[0].temp.min.roundToInt().toString() + "°" //detail
                    binding.currentFeelsLike.text =
                        it.current.feelsLike.roundToInt().toString() + "°" //detail


                    //5 hourly update textview
                    binding.hourlyIconOne.setImageDrawable(context?.getDrawable(setIcon(it.hourly[1].weather[0].icon)))
                    binding.tempOne.text = it.hourly[1].temp.roundToInt().toString()
                    binding.timeOne.text =
                        SimpleDateFormat("h:mm a").format(Date(it.hourly[1].dt.toLong() * 1000))

                    binding.hourlyIconTwo.setImageDrawable(context?.getDrawable(setIcon(it.hourly[2].weather[0].icon)))
                    binding.tempTwo.text = it.hourly[2].temp.roundToInt().toString()
                    binding.timeTwo.text =
                        SimpleDateFormat("h:mm a").format(Date(it.hourly[2].dt.toLong() * 1000))

                    binding.hourlyIconThree.setImageDrawable(context?.getDrawable(setIcon(it.hourly[3].weather[0].icon)))
                    binding.tempThree.text = it.hourly[3].temp.roundToInt().toString()
                    binding.timeThree.text =
                        SimpleDateFormat("h:mm a").format(Date(it.hourly[3].dt.toLong() * 1000))

                    binding.hourlyIconFour.setImageDrawable(context?.getDrawable(setIcon(it.hourly[4].weather[0].icon)))
                    binding.tempFour.text = it.hourly[4].temp.roundToInt().toString()
                    binding.timeFour.text =
                        SimpleDateFormat("h:mm a").format(Date(it.hourly[4].dt.toLong() * 1000))

                    binding.hourlyIconFive.setImageDrawable(context?.getDrawable(setIcon(it.hourly[5].weather[0].icon)))
                    binding.tempFive.text = it.hourly[5].temp.roundToInt().toString()
                    binding.timeFive.text =
                        SimpleDateFormat("h:mm a").format(Date(it.hourly[5].dt.toLong() * 1000))

                    //alerts
                    if (it.alerts != null) {
                        binding.alertSection.visibility = View.VISIBLE
                        binding.alertEvent.text = it.alerts[0].event.uppercase()
                        binding.alertDescrip.text = it.alerts[0].description
                        binding.alertSender.text = it.alerts[0].senderName

                    } else {
                        binding.alertSection.visibility = View.GONE
                    }

//                    setFragmentResult("key_to_weekly", bundleOf("daily" to it.daily))
                },
                onError = { e -> println("this is error $e") }
            )
    }


    fun getCurrentCity(latitude: String, longitude: String) {
        currentLocationViewModel.getCurrentCity("$latitude+$longitude", openCageDataKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    var city = it.results[0].components.city
                    if (city == null) city = it.results[0].components.county
                    var state = it.results[0].components.state
                    var country = it.results[0].components.country
                    var placeName = "$city, $state, $country"
                    autocompleteFragment.setHint(placeName)
                },
                onError = { e -> println("this is error $e") }
            )
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.degreeUnit -> {

//                binding.currentTemperature.setText("999999")
                if (units == "metric") {
                    units = "imperial"
                    currentW = root.findViewById(R.id.current_temperature)
//                    current_temperature.setText("889")
//                    binding.currentTemperature.setText("66")
                    currentW.text="699"
//                    getActivity()!!.runOnUiThread { binding.currentTemperature.setText("66") }
//                    R.id.current_temperature
                    currentW.postInvalidate()
                    println("gjggjjh")
                    println(current_temperature.text)

                    getCurrentWeather("45","-75")


                } else {
                    units = "metric"
                    current_temperature.setText("889")
                    getCurrentWeather("45","-75")

                }
//                getFragmentManager()!!.beginTransaction().detach(this).attach(this).commit()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
