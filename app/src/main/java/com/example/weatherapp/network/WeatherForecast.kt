package com.example.weatherapp.network

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecast(
    val list: List<ForecastEntry>,
    val city: City
)

@Serializable
data class ForecastEntry(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
)

@Serializable
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class City(
    val name: String,
    val coord: Coord,
    val country: String
)

@Serializable
data class Coord(
    val lat: Double,
    val lon: Double
)
