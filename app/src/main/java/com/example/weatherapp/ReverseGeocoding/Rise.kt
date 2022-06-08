package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Rise(
    val apparent: Int,
    val astronomical: Int,
    val civil: Int,
    val nautical: Int
)