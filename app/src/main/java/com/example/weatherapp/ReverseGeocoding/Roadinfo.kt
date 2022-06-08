package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Roadinfo(
    @SerializedName("drive_on")
    val driveOn: String,
    val road: String,
    @SerializedName("speed_in")
    val speedIn: String
)