package com.example.weatherapp.APIResponse


import com.example.weatherapp.DataBase.FavLocations
import com.google.gson.annotations.SerializedName

data class LocationWeather(
    val weather: AllWeather?,
    val favLocations: FavLocations
)