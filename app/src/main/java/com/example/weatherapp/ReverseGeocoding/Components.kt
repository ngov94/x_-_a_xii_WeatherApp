package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Components(
    @SerializedName("_category")
    val category: String,
    val city: String,
    val continent: String,
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    val county: String,
    @SerializedName("house_number")
    val houseNumber: String,
    @SerializedName("ISO_3166-1_alpha-2")
    val iSO31661Alpha2: String,
    @SerializedName("ISO_3166-1_alpha-3")
    val iSO31661Alpha3: String,
    @SerializedName("ISO_3166-2")
    val iSO31662: List<String>,
    val neighbourhood: String,
    val postcode: String,
    val road: String,
    val state: String,
    @SerializedName("state_code")
    val stateCode: String,
    @SerializedName("state_district")
    val stateDistrict: String,
    @SerializedName("_type")
    val type: String
)