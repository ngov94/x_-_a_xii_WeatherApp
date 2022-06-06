package com.example.weatherapp

import com.example.weatherapp.Response.CurrentWeather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetroApiInterface {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude:String,
        @Query("lon") longitude:String,
        @Query("appid") apikey:String,
        @Query("units") unit:String
    ): Response<CurrentWeather>



/// https://api.openweathermap.org/data/2.5/weather?lat=43.452969&lon=-80.495064&appid=2969a813a03aab47497e1da3a8cf1a81
    companion object {
        var BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create(): RetroApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetroApiInterface::class.java)
        }
    }


}
