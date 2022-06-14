package com.example.weatherapp.data.APIResponse


import com.example.weatherapp.data.DataBase.FavLocations

data class LocationWeather(
    val weather: AllWeather?,
    val favLocations: FavLocations
)