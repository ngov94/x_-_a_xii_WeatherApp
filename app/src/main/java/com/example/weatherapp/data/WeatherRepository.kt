package com.example.weatherapp.data

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.DataBase.*


class WeatherRepository(val inter: RetroApiInterface, private val dao: WeatherDao) {

  fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ) = inter.getCurrentWeather(latitude, longitude, apiKey, unit)

    fun getGeoloaction(googleApi: String) = inter.getGeoloaction(googleApi)

    fun getCurrentCity(latLng:String, cagedDataKey:String) = inter.getCurrentCity(latLng, cagedDataKey)

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
