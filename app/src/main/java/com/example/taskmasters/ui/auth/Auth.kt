@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmasters.ui.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreet(
    navController: NavController
) {
    val isEmailState = remember { mutableStateOf(false) }
    val isUserState = remember { mutableStateOf(false) }
    val isPasswordState = remember { mutableStateOf(false) }
    val userAuth = rememberUserAuth()
    val error = remember { mutableStateOf("")}

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val username = remember {
        mutableStateOf("")
    }

    Scaffold() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            Text(text = "Регистрация", fontSize = 50.sp)
            Space(10)
            TextFields(
                Icon = Icons.Default.Email,
                title = "email",
                KeyboardType.Email,
                State = isEmailState,
                email
            )
            TextFields(
                Icon = Icons.Default.Person,
                title = "username",
                KeyboardType.Text,
                State = isUserState,
                username
            )
            TextFields(
                Icon = Icons.Default.Lock,
                title = "password",
                KeyboardType.Password,
                State = isPasswordState,
                password
            )
            if (error.value.isNotEmpty()) {
                Text(text = error.value, fontSize = 20.sp, color = Color.Red)
            }
            Space(50)
            ContinueButton(
                isEmailFilled = isEmailState.value,
                isUsernameFilled = isUserState.value,
                isPasswordFilled = isPasswordState.value,
                "Зарегистрироваться",
            ) { userAuth.authWithEmail(email.value, password.value, onError = { exception ->
                error.value = exception
            }) }
            Space(50)
            Or(navController, "Или войдите в аккаунт", Screen.LogInScreen)
            Space(30)
            SignBlock(error)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    navController: NavController
) {
    val isEmailState = remember { mutableStateOf(false) }
    val isPasswordState = remember { mutableStateOf(false) }
    val isUserState = remember { mutableStateOf(true) }
    val userAuth = rememberUserAuth()
    val error = remember { mutableStateOf("")}

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    Scaffold() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            Text(text = "Вход", fontSize = 50.sp)
            Space(10)
            TextFields(
                Icon = Icons.Default.Email,
                title = "email",
                KeyboardType.Email,
                State = isEmailState,
                email
            )
            TextFields(
                Icon = Icons.Default.Person,
                title = "password",
                KeyboardType.Password,
                State = isPasswordState,
                password
            )
            if (error.value.isNotEmpty()) {
                Text(text = error.value, fontSize = 20.sp, color = Color.Red)
            }
            Space(50)
            ContinueButton(
                isEmailFilled = isEmailState.value,
                isUsernameFilled = isUserState.value,
                isPasswordFilled = isPasswordState.value,
                "Войти"
            ) {
                userAuth.signWithEmail(email.value, password.value, onError = { exception ->
                    error.value = exception
                })
            }
            Space(50)
            Or(navController, title = "Или зарегистрируйтесь", Screen.RegistrationScreet)
            Space(height = 50)
            GoogleButton(error)
        }
    }
}