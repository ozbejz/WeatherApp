package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.http.GET
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.network.ForecastEntry
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.ui.theme.DataUiState
import com.example.weatherapp.ui.theme.WeatherTheme
import com.example.weatherapp.ui.theme.WeatherViewModel
import java.io.IOException
import kotlin.math.round

@Composable
fun Search(modifier: Modifier,
           weatherViewModel: WeatherViewModel
){
    var text: String by remember { mutableStateOf("") }
    val weatherUiState by weatherViewModel.uiState.collectAsState()

    val loggedIn = weatherUiState.loggedIn

    if(weatherUiState.loggedIn == false){
        SignUp(weatherViewModel = weatherViewModel)
    }
    else{
    Column(
        modifier = modifier.fillMaxSize()
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(
                value = weatherViewModel.location,
                onValueChange = {
                    weatherViewModel.location = it
                },
                singleLine = true,
                label = { Text(text = "Enter location") },
            )

            Button(
                onClick = { weatherViewModel.searchWeather() },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SearchDisplay(weatherViewModel)

    }}
}

@Composable
fun SearchDisplay(weatherViewModel: WeatherViewModel){
    val forecast = weatherViewModel.searchData

    forecast?.forEach { forecast ->
        DayDisplay(forecast)
    }
    if(weatherViewModel.searchError != null){
        Text(text = weatherViewModel.searchError!!, color = MaterialTheme.colorScheme.error)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    WeatherTheme {
        Search(modifier = Modifier, viewModel())
    }
}
