package com.example.weatherapp.ReverseGeocoding


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("alternate_symbols")
    val alternateSymbols: List<String>,
    @SerializedName("decimal_mark")
    val decimalMark: String,
    @SerializedName("disambiguate_symbol")
    val disambiguateSymbol: String,
    @SerializedName("html_entity")
    val htmlEntity: String,
    @SerializedName("iso_code")
    val isoCode: String,
    @SerializedName("iso_numeric")
    val isoNumeric: String,
    val name: String,
    @SerializedName("smallest_denomination")
    val smallestDenomination: Int,
    val subunit: String,
    @SerializedName("subunit_to_unit")
    val subunitToUnit: Int,
    val symbol: String,
    @SerializedName("symbol_first")
    val symbolFirst: Int,
    @SerializedName("thousands_separator")
    val thousandsSeparator: String
)