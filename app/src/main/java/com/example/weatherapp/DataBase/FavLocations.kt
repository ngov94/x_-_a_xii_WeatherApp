package com.example.weatherapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favlocations")
data class FavLocations(
    val placeName: String,
    val latitude: String,
    val longitude: String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}


