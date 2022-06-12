package com.example.weatherapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.APIResponse.LocationWeather
import com.example.weatherapp.DataBase.FavLocations
import com.example.weatherapp.WeatherRepository
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

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun insertFavLocation(favLocation: FavLocations) = repo.insertFavLocation(favLocation)

    fun getFavLocationWeatherList(favLocation: List<FavLocations>, apiKey: String, unit: String){
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            var locationWeather = ArrayList<LocationWeather>()

            for(loc in favLocation) {
                var res = repo.getCurrentWeather(loc.latitude, loc.longitude, apiKey, unit)

                if (res.isSuccessful) {
                    locationWeather.add(LocationWeather(res.body(),loc))
                }

            }
            favLocationWeatherList.postValue(locationWeather)
        }
    }

    fun deleteFavLocation(favLocation: FavLocations) = repo.deleteFavLocation(favLocation)

}