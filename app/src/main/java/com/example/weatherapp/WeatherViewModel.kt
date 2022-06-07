package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.APIResponse.AllWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(val repo: WeatherRepository) : ViewModel() {
    var currentWeather = MutableLiveData<AllWeather>()


    fun getCurrentWeather(latitude: String, longitude: String, apiKey: String, unit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getCurrentWeather(latitude, longitude, apiKey, unit)
            if(res.isSuccessful) {
                currentWeather.postValue(res.body())
            }

        }
    }

}