package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.network.ForecastEntry
import com.example.weatherapp.ui.theme.DataUiState
import com.example.weatherapp.ui.theme.WeatherTheme
import com.example.weatherapp.ui.theme.WeatherViewModel
import kotlin.math.round

@Composable
fun DisplayWeather(
                   modifier: Modifier,
                   dataUiState: DataUiState
){
    when (dataUiState) {
        is DataUiState.Loading -> Text(text = "loading")
        is DataUiState.Success -> ResultScreen(
            dataUiState.forecast, modifier = modifier.fillMaxWidth()
        )
        is DataUiState.Error -> Text("Error")
        else -> {}
    }
}

@Composable
fun ResultScreen(forecast: List<ForecastEntry>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        forecast.forEach { forecast ->
            DayDisplay(forecast)
        }
    }
}

@Composable
fun DayDisplay(forecast: ForecastEntry){
    val imageResource = when (forecast.weather.firstOrNull()?.icon) {
        "01d" -> R.drawable._01d
        "02d" -> R.drawable._02d
        "03d" -> R.drawable._03d
        "04d" -> R.drawable._04d
        "09d" -> R.drawable._09d
        "10d" -> R.drawable._10d
        "11d" -> R.drawable._11d
        "13d" -> R.drawable._13d
        "50d" -> R.drawable._50d
        else -> R.drawable._10d
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val formattedDate = forecast.dt_txt.split(" ")[0]
        val temp = round(forecast.main.temp).toInt()

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = "1"
            )
            Text(
                text = "Date: $formattedDate",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Temperature: $temp °C",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Condition: ${forecast.weather.firstOrNull()?.description}",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayWeatherPreview() {
    WeatherTheme {
        DisplayWeather(modifier = Modifier, viewModel())
    }
}
