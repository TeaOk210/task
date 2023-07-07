package com.example.taskmasters.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.ui.auth.RegistrationScreet
import com.example.taskmasters.ui.auth.Screen
import com.example.taskmasters.ui.auth.SignInScreen
import com.example.taskmasters.ui.calendar.CalendarScreen
import com.example.taskmasters.ui.home.HomeScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.taskmasters.ui.auth.UserAuth
import com.example.taskmasters.ui.auth.rememberUserAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskMasterApp(
    navController: NavHostController = rememberNavController(),
    appState: UserAuth = rememberUserAuth()
) {
    val userState = appState.userState
//    LaunchedEffect(userState) {
//        if (userState) {
//            navController.navigate(BottomNavScreen.Home.route) {
//                popUpTo(navController.graph.startDestinationId)
//                launchSingleTop = true
//            }
//        } else {
//            navController.navigate(Screen.RegistrationScreet.route) {
//                popUpTo(navController.graph.startDestinationId)
//                launchSingleTop = true
//            }
//        }
//    }

    NavHost(navController = navController, startDestination = if (userState) BottomNavScreen.Home.route else Screen.RegistrationScreet.route) {
        composable(Screen.RegistrationScreet.route) {
            RegistrationScreet(navController)
        }
        composable(Screen.LogInScreen.route) {
            SignInScreen(navController)
        }
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