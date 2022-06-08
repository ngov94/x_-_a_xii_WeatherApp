package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Regions(
    @SerializedName("AMERICAS")
    val aMERICAS: String,
    @SerializedName("CA")
    val cA: String,
    @SerializedName("NORTHERN_AMERICA")
    val nORTHERNAMERICA: String,
    @SerializedName("WORLD")
    val wORLD: String
)