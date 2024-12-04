package com.example.weatherapp.network
import android.icu.util.Currency
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.openweathermap.org/data/2.5/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiInterface{
    @GET("forecast")
    suspend fun getPhotos(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): WeatherForecast
}

object WeatherApi{
    val retrofitService : WeatherApiInterface by lazy {
        retrofit.create(WeatherApiInterface::class.java)
    }
}