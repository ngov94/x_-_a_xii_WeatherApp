package com.example.weatherapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.APIResponse.AllWeather
import com.example.weatherapp.data.DataBase.FavLocations
import com.example.weatherapp.data.WeatherRepository
import io.reactivex.rxjava3.core.Observable

class LocationViewModel(val repo: WeatherRepository) : ViewModel() {

    val favLocationsList : LiveData<List<FavLocations>>


    init {
        favLocationsList = repo.getFavLocationsList()

    }



    fun insertFavLocation(favLocation: FavLocations) = repo.insertFavLocation(favLocation)

    fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ): Observable<AllWeather> {
        return repo.getCurrentWeather(latitude, longitude, apiKey, unit)
    }

    fun deleteFavLocation(favLocation: FavLocations) = repo.deleteFavLocation(favLocation)

}