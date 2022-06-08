package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Timestamp(
    @SerializedName("created_http")
    val createdHttp: String,
    @SerializedName("created_unix")
    val createdUnix: Int
)