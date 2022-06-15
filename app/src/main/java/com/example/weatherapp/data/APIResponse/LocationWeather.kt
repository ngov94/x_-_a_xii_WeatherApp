package com.example.weatherapp.data.APIResponse


import com.example.weatherapp.data.DataBase.FavLocations

data class LocationWeather(
    var currentTemp: Double,
    var maxTemp: Double,
    var minTemp: Double,
    val pop: Double,
    val icon: String,
    val favLocations: FavLocations
)