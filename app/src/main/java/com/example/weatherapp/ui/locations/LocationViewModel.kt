package com.example.weatherapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.APIResponse.LocationWeather
import com.example.weatherapp.DataBase.FavLocations
import com.example.weatherapp.WeatherRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(val repo: WeatherRepository) : ViewModel() {

    val favLocationsList : LiveData<List<FavLocations>>
    val favLocationWeatherList = MutableLiveData<List<LocationWeather>>()


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