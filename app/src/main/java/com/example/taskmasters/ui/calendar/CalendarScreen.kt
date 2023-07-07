package com.example.taskmasters.ui.calendar

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Surface(
            elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
                .padding(
                    start = 5.dp, end = 5.dp, top = 30.dp
                )
        ) {
            AndroidView(factory = { CalendarView(it) }, update = {
            })
        }
    }
}