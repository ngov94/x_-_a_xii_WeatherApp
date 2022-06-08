package com.example.weatherapp.GeolocationApi


import com.google.gson.annotations.SerializedName

data class Geolocation(
    val accuracy: Double,
    val location: Location
)