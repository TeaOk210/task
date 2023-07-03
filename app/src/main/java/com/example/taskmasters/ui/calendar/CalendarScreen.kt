package com.example.taskmasters.ui.calendar

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.taskmasters.screens.BottomBar
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.screens.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = { TopBar(BottomNavScreen.Calendar) }
    ) {
        AndroidView(factory = { CalendarView(it) }, update = {
//            it.setOnDateChangeListener()
        })
    }
}