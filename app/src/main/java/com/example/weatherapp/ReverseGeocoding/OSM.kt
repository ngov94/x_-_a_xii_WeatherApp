package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class OSM(
    @SerializedName("edit_url")
    val editUrl: String,
    @SerializedName("note_url")
    val noteUrl: String,
    val url: String
)