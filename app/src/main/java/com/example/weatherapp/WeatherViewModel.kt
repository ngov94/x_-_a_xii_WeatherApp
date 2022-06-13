package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.APIResponse.LocationWeather
import com.example.weatherapp.DataBase.AllWeatherEntity
import com.example.weatherapp.DataBase.CityLatLong
import com.example.weatherapp.DataBase.FavLocations
import com.example.weatherapp.DataBase.PlaceName
import com.example.weatherapp.GeolocationApi.Geolocation
import com.example.weatherapp.ReverseGeocoding.CurrentCity
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(val repo: WeatherRepository) : ViewModel() {
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is current location Fragment"
//    }
//    val text: LiveData<String> = _text

    var currentWeather = MutableLiveData<AllWeather>()
    var currentLocation = MutableLiveData<Geolocation>()
    var currentCity = MutableLiveData<CurrentCity>()

    val getAllWeather : LiveData<AllWeatherEntity>
    val getLatLong : LiveData<CityLatLong>



    init {
        getAllWeather = repo.getAllWeather()
        getLatLong = repo.getLatLong()


    }

//    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
//        throwable.printStackTrace()
//    }


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


    fun insertWeather(weather: AllWeatherEntity) = viewModelScope.launch{
        repo.insertWeather(weather)
    }


    fun insertLatLong(cityLatLong: CityLatLong) = viewModelScope.launch {
        repo.insertLatLong(cityLatLong)
    }


    fun getPlaceName() = repo.getPlaceName()



    fun insertPlaceName(placeName: PlaceName) = viewModelScope.launch {
        repo.insertPlaceName(placeName)
    }





}