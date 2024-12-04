package com.example.weatherapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.ForecastEntry
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.network.WeatherForecast
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DataUiState {
    data class Success(val forecast: List<ForecastEntry>) : DataUiState
    object Error : DataUiState
    object Loading : DataUiState
}

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    var userPass by mutableStateOf("")

    var dataUiState: DataUiState by mutableStateOf(DataUiState.Loading)
        private set

    var location by mutableStateOf("")
    var searchData by mutableStateOf<List<ForecastEntry>?>(null)
    var searchError by mutableStateOf<String?>(null)

    init {
        getData()
    }

    fun checkPass(){
        if(userPass == "geslo"){
            _uiState.update { currentState -> currentState.copy(loggedIn = true) }
            _uiState.update { currentState -> currentState.copy(wrongPass = false) }
        }
        else{
            _uiState.update { currentState -> currentState.copy(wrongPass = true) }
        }
    }

    fun updatePass(pass: String){
        userPass = pass
    }

    fun getData(){
        viewModelScope.launch {
            dataUiState = DataUiState.Loading
            dataUiState = try {
                val listResult = WeatherApi.retrofitService.getPhotos("ljubljana", "Metric", "c3270ee2bf66c7465a5b89c3ee60cc2b")

                val noonForecasts = listResult.list.filter { it.dt_txt.endsWith("12:00:00") }

                DataUiState.Success(noonForecasts)
            } catch (e: IOException) {
                DataUiState.Error
            }
        }
    }

    fun searchWeather(){
        viewModelScope.launch {
            try {
                val listResult = WeatherApi.retrofitService.getPhotos(location, "Metric", "c3270ee2bf66c7465a5b89c3ee60cc2b")

                searchData = listResult.list.filter { it.dt_txt.endsWith("12:00:00") }
                searchError = null
            }catch (e: HttpException) {
                searchData = null
                searchError = "Can't find city"
            }  catch (e: IOException) {
                searchData = null
            }
        }
    }
}