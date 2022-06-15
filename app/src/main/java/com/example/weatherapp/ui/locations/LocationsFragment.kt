package com.example.weatherapp.ui.locations


import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.*
import com.example.weatherapp.data.APIResponse.LocationWeather
import com.example.weatherapp.data.DataBase.FavLocations
import com.example.weatherapp.data.DataBase.WeatherDatabase
import com.example.weatherapp.data.RetroApiInterface
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.limited_favorites_inflatable.view.*
import kotlin.math.roundToInt


class LocationsFragment : Fragment() {

    private var _binding: ActivityLocationsFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var placesClient: PlacesClient
    val locWeatherList = ArrayList<LocationWeather>()
    val adapter = LocationAdapter(locWeatherList)
    lateinit var locationViewModel: LocationViewModel
    var units = "metric"  //imperial
    val googleApi = BuildConfig.GOOGLE_KEY // Google Cloud API
    val weatherApiKey = BuildConfig.WEATHER_KEY

    var locationWeather = ArrayList<LocationWeather>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        val intr = RetroApiInterface.create()
        val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
        val repo = WeatherRepository(intr, dao)
        locationViewModel = LocationViewModel(repo)


        _binding = ActivityLocationsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.favoritesRecycler.adapter = adapter
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(activity)


        binding.addButton.setOnClickListener {
            Places.initialize(requireContext(), googleApi)
            placesClient = Places.createClient(requireContext())
            val AUTOCOMPLETE_REQUEST_CODE = 1

            val fields = listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.CITIES)
                .build(this.requireContext())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
//        <---End of Codes for Google autocomplete Fragment --->


        locationViewModel.favLocationsList.observe(viewLifecycleOwner) {
            locationWeather.clear()
            for (loc in it) {
                locationViewModel.getCurrentWeather(
                    loc.latitude,
                    loc.longitude,
                    weatherApiKey,
                    units
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onNext = {
                            var currentTemp = it.current.temp
                            var maxTemp = it.daily.first().temp.max
                            var minTemp = it.daily.first().temp.min
                            var pop = it.daily.first().pop
                            var icon = it.daily.first().weather.first().icon
                            locationWeather.add(LocationWeather(currentTemp, maxTemp, minTemp, pop, icon ,loc))
                        },
                        onComplete = {
                            locWeatherList.clear()
                            locationWeather.sortByDescending {it.favLocations.id}
                            locWeatherList.addAll(locationWeather)
                            adapter.notifyDataSetChanged()
                        },
                        onError = { e -> println("this is error $e") }

                    )
            }
        }

        //Delete Fav Location
        adapter.setOnItemLongClickListener(object : LocationAdapter.OnItemLongClickListener {
            override fun onItemLongClick(itemView: View) {
                var locId = itemView.fav_item_location_id.text.toString().toInt()
                var locName = itemView.fav_item_location.text.toString()
                var locLat = itemView.fav_item_location_lat.text.toString()
                var locLong = itemView.fav_item_location_long.text.toString()

                var deleteLocation = FavLocations(locId, locName, locLat, locLong)

                AlertDialog.Builder(context)
                    .setTitle("Do you want to remove $locName?")
                    .setNegativeButton("No") { _, _ ->
                        Toast.makeText(context, "$locName not deleted", Toast.LENGTH_LONG).show()
                    }
                    .setPositiveButton("Yes") { _, _ ->
                        locationViewModel.deleteFavLocation(deleteLocation)
                        if(locWeatherList.size == 1){
                            locWeatherList.clear()
                            adapter.notifyDataSetChanged()
                        }
                        Toast.makeText(context, "$locName is deleted", Toast.LENGTH_LONG).show()
                    }.show()
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        var placeName = place.address!!.split(",")
                        var shortPlaceName = placeName[0]+", "+placeName[1]
                        var latitude = place.latLng?.latitude.toString()
                        var longitude = place.latLng?.longitude.toString()
                        locationViewModel.insertFavLocation(
                            FavLocations(
                                placeName = shortPlaceName,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.action_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.degreeUnit -> {
                if (units.equals("metric")) {
                    units = "imperial"
                    item.setIcon(R.drawable.unit_imperial)
                    item.setTitle("Imperial Units")
                    for(loc in locWeatherList){
                        loc.currentTemp = (loc.currentTemp * (9.0/5.0)) + 32.0
                        loc.maxTemp = (loc.maxTemp * (9.0/5.0)) + 32.0
                        loc.minTemp = (loc.minTemp * (9.0/5.0)) + 32.0
                    }
                    adapter.notifyDataSetChanged()

                } else {
                    units = "metric"
                    item.setIcon(R.drawable.unit_metric)
                    item.setTitle("Metric Units")
                    for(loc in locWeatherList){
                        loc.currentTemp = (loc.currentTemp - 32.0) * (5.0/9.0)
                        loc.maxTemp = (loc.maxTemp - 32.0) * (5.0/9.0)
                        loc.minTemp = (loc.minTemp - 32.0) * (5.0/9.0)
                    }
                    adapter.notifyDataSetChanged()
                }

                super.onOptionsItemSelected(item)
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}

