package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Timezone(
    val name: String,
    @SerializedName("now_in_dst")
    val nowInDst: Int,
    @SerializedName("offset_sec")
    val offsetSec: Int,
    @SerializedName("offset_string")
    val offsetString: String,
    @SerializedName("short_name")
    val shortName: String
)