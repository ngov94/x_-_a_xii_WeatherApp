package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class CurrentCity(
    val documentation: String,
    val licenses: List<License>,
    val rate: Rate,
    val results: List<Result>,
    val status: Status,
    @SerializedName("stay_informed")
    val stayInformed: StayInformed,
    val thanks: String,
    val timestamp: Timestamp,
    @SerializedName("total_results")
    val totalResults: Int
)