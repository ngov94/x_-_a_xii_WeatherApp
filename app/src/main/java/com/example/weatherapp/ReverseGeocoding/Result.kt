package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Result(
    val annotations: Annotations,
    val bounds: Bounds,
    val components: Components,
    val confidence: Int,
    val formatted: String,
    val geometry: Geometry
)