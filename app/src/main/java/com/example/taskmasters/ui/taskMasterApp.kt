package com.example.taskmasters.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.ui.auth.RegistrationScreet
import com.example.taskmasters.ui.auth.Screen
import com.example.taskmasters.ui.auth.SignInScreen
import com.example.taskmasters.ui.calendar.CalendarScreen
import com.example.taskmasters.ui.home.HomeScreen
import com.example.taskmasters.ui.settings.SettingsScreen
import com.example.taskmasters.ui.table.TableScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskMasterApp(
    navController: NavHostController = rememberNavController(),
    auth: FirebaseAuth = Firebase.auth
) {
    val user = auth.currentUser

    NavHost(
        navController = navController,
        startDestination = if (user != null) BottomNavScreen.Home.route else Screen.RegistrationScreet.route
    ) {
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
            SettingsScreen(navController)
        }
        composable("${BottomNavScreen.Table.route}/{tableId}") { backStackEntry ->
            val args = requireNotNull(backStackEntry.arguments)
            val tableId = args.getString("tableId")?.toInt()
            TableScreen(navController, tableId!!)
        }
    }
}