package com.example.weatherapp.ui.theme

data class WeatherUiState(
    val pass: String = "",
    val loggedIn: Boolean = false,
    val wrongPass: Boolean = false,
    val data: String = "",
    val dataLoaded: Boolean = false
)