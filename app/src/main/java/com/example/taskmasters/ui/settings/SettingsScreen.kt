@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmasters.ui.settings

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.taskmasters.screens.BottomBar
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.screens.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    
    Scaffold(
        bottomBar = { BottomBar(navController = navController)},
        topBar = { TopBar(screen = BottomNavScreen.Settings)}
    ) {
        
    }
}