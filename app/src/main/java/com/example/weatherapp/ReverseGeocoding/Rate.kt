package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Rate(
    val limit: Int,
    val remaining: Int,
    val reset: Int
)