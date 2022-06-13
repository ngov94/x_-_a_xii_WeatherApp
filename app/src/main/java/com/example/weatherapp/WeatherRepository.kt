package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.DataBase.*
import retrofit2.Response


class WeatherRepository(val inter: RetroApiInterface, private val dao: WeatherDao) {

  fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ) = inter.getCurrentWeather(latitude, longitude, apiKey, unit)

    fun getGeoloaction(googleApi: String) = inter.getGeoloaction(googleApi)

    fun getCurrentCity(latLng:String, cagedDataKey:String) = inter.getCurrentCity(latLng, cagedDataKey)

    fun getAllWeather(): LiveData<AllWeatherEntity> {
        return dao.getAllWeather()
    }

    fun insertWeather(weather: AllWeatherEntity) {
        return dao.insertWeather(weather)
    }

    fun getLatLong(): LiveData<CityLatLong> {
        return dao.getLatLong()
    }

    fun insertLatLong(cityLatLong: CityLatLong) {
        return dao.insertLatLong(cityLatLong)
    }

    fun getPlaceName(): PlaceName {
        return dao.getPlaceName()
    }

    fun insertPlaceName(placeName: PlaceName) {
        return dao.insertPlaceName(placeName)
    }

    // For Fav Locations
    fun getFavLocationsList():LiveData<List<FavLocations>>{
        return dao.getFavLocationsList()
    }

    fun insertFavLocation(favLocation: FavLocations){
        return dao.insertFavLocation(favLocation)
    }

    fun deleteFavLocation(favLocation: FavLocations){
        return dao.deleteFavLocation(favLocation)
    }
}
