@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmasters.ui.table

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import com.example.taskmasters.screens.TopBar
import com.example.taskmasters.ui.home.checkColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TableScreen(
    navController: NavController,
    tableId: Int,
    viewModel: TableViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadTable(tableId)
    }

    val table by viewModel.table.collectAsState()

    Scaffold(
        topBar = { TopBar(table.userId) }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = table.colors.checkColor(),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                    alpha = 0.6f
                )
        ) {
            // Ваше содержимое экрана
        }

    }
}