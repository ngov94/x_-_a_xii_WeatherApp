package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.GeolocationApi.Geolocation
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