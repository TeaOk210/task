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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
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
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = { TopBar(BottomNavScreen.Home) }
    ) {
        val cardHeight by mutableIntStateOf(500)

        TableCard(cardHeight.dp, "Рабочие столы", viewModel)
    }
}

@Composable
fun TableCard(
    height: Dp,
    title: String,
    viewModel: HomeViewModel
) {
//    val userId = rememberUserState().Userid
//    LaunchedEffect(userId) {
//        viewModel.getTables(userId)
//    }

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
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = { viewModel.addTable(
                    com.example.taskmasters.data.TableItem(
                        "",
                        Color.Cyan
                    )
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(Icons.Default.Add, "Add table button")
            }
//            val currentDesktop by viewModel.tables
//
//            currentDesktop.forEach { tableItem ->
//                TableItem(color = tableItem.color)
//            }
        }
    }
}

@Composable
fun TableItem(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color)
    )
}