package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class UNM49(
    val regions: Regions,
    @SerializedName("statistical_groupings")
    val statisticalGroupings: List<String>
)