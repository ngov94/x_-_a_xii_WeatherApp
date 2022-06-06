package com.example.weatherapp


class WeatherRepository(val inter: RetroApiInterface) {

    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
        unit: String
    ) = inter.getCurrentWeather(latitude, longitude, apiKey, unit)
}