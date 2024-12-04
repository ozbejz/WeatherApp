package com.example.weatherapp

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.WeatherViewModel

@Composable
fun SignUp(weatherViewModel: WeatherViewModel){

    val weatherUiState by weatherViewModel.uiState.collectAsState()
    val pass = weatherViewModel.userPass

    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { //weatherViewModel.updatePass(it)
                input = it},
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            label = {
                if(weatherUiState.wrongPass == false){
                    Text(text = "Enter password")
                }
                else{
                    Text(text = "Wrong password!",
                        color = MaterialTheme.colorScheme.error)
                }
            },
            isError = weatherUiState.wrongPass,
        )

        Button(
            onClick = {
                weatherViewModel.updatePass(input)
                weatherViewModel.checkPass()
            }
        ) {
            Text(text = "Sign In")
        }
    }

}