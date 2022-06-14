package com.example.weatherapp.data.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {

    //For Favourite Locations Page
    @Query("SELECT * from favlocations ORDER BY id DESC")
    fun getFavLocationsList():LiveData<List<FavLocations>>

    @Insert
    fun insertFavLocation(favLocation: FavLocations)

    @Delete
    fun deleteFavLocation(favLocation: FavLocations)


}