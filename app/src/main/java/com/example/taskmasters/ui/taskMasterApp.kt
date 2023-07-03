package com.example.taskmasters.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.ui.auth.RegistrationScreet
import com.example.taskmasters.ui.auth.Screen
import com.example.taskmasters.ui.auth.SignInScreen
import com.example.taskmasters.ui.auth.UserAuth
import com.example.taskmasters.ui.auth.rememberUserAuth
import com.example.taskmasters.ui.calendar.CalendarScreen
import com.example.taskmasters.ui.home.HomeScreen

@Composable
fun TaskMasterApp(
    appState: UserAuth = rememberUserAuth(),
    navController: NavHostController = appState.navController
) {
    val userState = appState.userState

    LaunchedEffect( userState ) {
        navController.navigate(if (userState) BottomNavScreen.Home.route else Screen.RegistrationScreet.route)
    }

    if (!appState.userState) {
        NavHost(navController = navController, startDestination = Screen.RegistrationScreet.route ) {
            composable(Screen.RegistrationScreet.route) {
                RegistrationScreet(navController)
            }
            composable(Screen.LogInScreen.route) {
                SignInScreen(navController)
            }
        }
    } else {
        NavHost(navController = navController, startDestination = BottomNavScreen.Home.route ) {
            composable(BottomNavScreen.Home.route) {
                HomeScreen(navController)
            }
            composable(BottomNavScreen.Calendar.route) {
                CalendarScreen(navController)
            }
            composable(BottomNavScreen.Settings.route) {

            }
        }
    }
}