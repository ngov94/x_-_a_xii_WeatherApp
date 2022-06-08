package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.GeolocationApi.Geolocation
import com.example.weatherapp.ReverseGeocoding.CurrentCity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(val repo: WeatherRepository) : ViewModel() {
    var currentWeather = MutableLiveData<AllWeather>()
    var currentLocation = MutableLiveData<Geolocation>()
    var currentCity = MutableLiveData<CurrentCity>()


    fun getCurrentWeather(latitude: String, longitude: String, apiKey: String, unit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getCurrentWeather(latitude, longitude, apiKey, unit)
            if(res.isSuccessful) {
                currentWeather.postValue(res.body())
            }

        }
    }


    fun getGeoloaction(googleApi: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getGeoloaction(googleApi)
            if(res.isSuccessful) {
                currentLocation.postValue(res.body())
            }

        }
    }

    fun getCurrentCity(latLng:String, cagedDataKey:String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getCurrentCity(latLng, cagedDataKey)
            if(res.isSuccessful) {
                currentCity.postValue(res.body())
            }

        }
    }
}