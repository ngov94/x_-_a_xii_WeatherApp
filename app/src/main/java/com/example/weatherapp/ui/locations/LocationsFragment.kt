package com.example.weatherapp.ui.locations

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.RetroApiInterface
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.databinding.ActivityLocationsFragmentBinding
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_locations_fragment.*
import java.util.*


class LocationsFragment : Fragment() {

    private var _binding: ActivityLocationsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locationsViewModel =
            ViewModelProvider(this).get(LocationsViewModel::class.java)

        _binding = ActivityLocationsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLocations
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
