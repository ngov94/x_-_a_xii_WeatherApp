package com.example.weatherapp.APIResponse


import com.google.gson.annotations.SerializedName

data class LocationWeather(
    val weather: AllWeather,
    val placename: String
)