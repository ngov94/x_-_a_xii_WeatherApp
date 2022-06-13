package com.example.weatherapp

import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.GeolocationApi.Geolocation
import com.example.weatherapp.ReverseGeocoding.CurrentCity
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetroApiInterface {



    @GET("onecall")
    fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apikey: String,
        @Query("units") unit: String,
//        @Query("exclude") exclude:String = "minutely,hourly,alerts"
    ): Observable<AllWeather>

    @POST("https://www.googleapis.com/geolocation/v1/geolocate")
    fun getGeoloaction(
        @Query("key") googleApi: String
    ): Observable<Geolocation>


    @GET("https://api.opencagedata.com/geocode/v1/json")
    fun getCurrentCity(
        @Query("q") latLng: String,
        @Query("key") cagedDataKey: String
    ): Observable<CurrentCity>


    /// https://api.openweathermap.org/data/2.5/weather?lat=43.452969&lon=-80.495064&appid=2969a813a03aab47497e1da3a8cf1a81
    companion object {
        var BASE_URL = "https://api.openweathermap.org/data/3.0/"
        fun create(): RetroApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetroApiInterface::class.java)
        }
    }


}
