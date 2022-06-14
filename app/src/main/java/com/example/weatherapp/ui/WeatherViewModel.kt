package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.APIResponse.AllWeather
import com.example.weatherapp.data.GeolocationApi.Geolocation
import com.example.weatherapp.data.WeatherRepository
import io.reactivex.rxjava3.core.Observable

class WeatherViewModel(val repo: WeatherRepository) : ViewModel() {


    fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ): Observable<AllWeather> {
        return repo.getCurrentWeather(latitude, longitude, apiKey, unit)
    }

    fun getGeoloaction(googleApi: String): Observable<Geolocation> {
        return repo.getGeoloaction(googleApi)
    }

    fun getCurrentCity(latLng: String, cagedDataKey: String) = repo.getCurrentCity(latLng, cagedDataKey)





}