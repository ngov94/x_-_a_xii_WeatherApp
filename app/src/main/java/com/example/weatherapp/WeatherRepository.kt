package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.DataBase.*
import retrofit2.Response


class WeatherRepository(val inter: RetroApiInterface, private val dao: WeatherDao) {

    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ) = inter.getCurrentWeather(latitude, longitude, apiKey, unit)

    suspend fun getGeoloaction(googleApi: String) = inter.getGeoloaction(googleApi)

    suspend fun getCurrentCity(latLng:String, cagedDataKey:String) = inter.getCurrentCity(latLng, cagedDataKey)

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

//    suspend fun getFavLocationWeatherList(favLocations: List<FavLocations>, apiKey: String, unit: String): LiveData<List<Response<AllWeather>>>{
//
//        var favLocationWeatherArray = ArrayList<Response<AllWeather>>()
//        for(loc in favLocations){
//            favLocationWeatherArray.add(inter.getCurrentWeather(loc.latitude, loc.longitude, apiKey, unit))
//        }
//
//        return MutableLiveData(favLocationWeatherArray)
//    }
}
