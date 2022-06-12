package com.example.weatherapp.APIResponse


import com.google.gson.annotations.SerializedName

data class Minutely(
    val dt: Int,
    val precipitation: Double
)