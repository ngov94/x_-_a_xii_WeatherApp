package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Annotations(
    val callingcode: Int,
    val currency: Currency,
    @SerializedName("DMS")
    val dMS: DMS,
    val flag: String,
    val geohash: String,
    @SerializedName("MGRS")
    val mGRS: String,
    @SerializedName("Maidenhead")
    val maidenhead: String,
    @SerializedName("Mercator")
    val mercator: Mercator,
    @SerializedName("OSM")
    val oSM: OSM,
    val qibla: Double,
    val roadinfo: Roadinfo,
    val sun: Sun,
    val timezone: Timezone,
    @SerializedName("UN_M49")
    val uNM49: UNM49,
    val what3words: What3words
)