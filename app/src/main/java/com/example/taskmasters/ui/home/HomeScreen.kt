@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmasters.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskmasters.screens.BottomBar
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.screens.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val tables by viewModel.tables.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTables()
    }

    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = { TopBar(BottomNavScreen.Home) },
    ) {
        val cardHeight by mutableIntStateOf(500)

        TableCard(cardHeight.dp, "Рабочие столы", tables, viewModel)
    }
}

@Composable
fun TableCard(
    height: Dp,
    title: String,
    tables: List<TableItem?>,
    viewModel: HomeViewModel
) {
    Text(text = title, fontSize = 26.sp, modifier = Modifier.padding(start = 15.dp, top = 115.dp))

    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = 10.dp, end = 10.dp, top = 150.dp)
            .background(Color.White),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = {
                    viewModel.addTable(Color.Cyan)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 30.dp
                )
            ) {
                Icon(Icons.Default.Add, "Add table button")
            }

            tables.forEach { tableItem ->
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(tableItem!!.color)
                )
            }
        }
    }
}