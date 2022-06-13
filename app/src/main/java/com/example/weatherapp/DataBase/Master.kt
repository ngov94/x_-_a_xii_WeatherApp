package com.example.weatherapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "master")
data class Master(
    val unit: String
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}


