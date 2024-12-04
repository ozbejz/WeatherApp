package com.example.weatherapp.ui

import android.graphics.drawable.Icon
import android.window.SplashScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.DisplayWeather
import com.example.weatherapp.Search
import com.example.weatherapp.SignUp
import com.example.weatherapp.ui.theme.WeatherViewModel
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.R

enum class Weather(@StringRes val title: Int){
    DisplayWeather(title = R.string.app_name),
    SignUp(title = R.string.sign_in),
    Search(title = R.string.search)
}

@Composable
fun Home(weatherViewModel: WeatherViewModel = viewModel(),
         navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Weather.valueOf(
        backStackEntry?.destination?.route ?: Weather.DisplayWeather.name
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                currentRoute.toString(),
                navigateUp = {navController.navigateUp() },
                navController
            )
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Weather.DisplayWeather.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Weather.DisplayWeather.name) {
                DisplayWeather(modifier = Modifier, dataUiState = weatherViewModel.dataUiState)
            }
            composable(route = Weather.SignUp.name) {
                SignUp(weatherViewModel)
            }
            composable(route = Weather.Search.name){
                Search(modifier = Modifier,weatherViewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppTopBar(
    currentScreen: Weather,
    canNavigateBack: Boolean,
    current: String,
    navigateUp: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary),
        title = { Text("Weather - Ljubljana") },
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if(current != "Search"){
                Button(onClick = {
                    navController.navigate("Search")}) {
                    Text(text = "Search")
                }
            }
        }
    )
}