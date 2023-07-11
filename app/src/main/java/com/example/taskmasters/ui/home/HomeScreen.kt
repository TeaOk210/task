@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmasters.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskmasters.data.TableItem
import com.example.taskmasters.screens.BottomBar
import com.example.taskmasters.screens.BottomNavScreen
import com.example.taskmasters.screens.Space
import com.example.taskmasters.screens.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        val cardHeight = remember { mutableStateOf(calculateCardHeight(tables.size)) }

        LaunchedEffect(key1 = tables.size) {
            cardHeight.value = calculateCardHeight(tables.size)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(115.dp))

            TableCard(cardHeight.value, "Рабочие столы", tables.sortedByDescending { it?.tableId }, viewModel, navController)

            Spacer(Modifier.height(100.dp))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TableCard(
    height: Dp,
    title: String,
    tables: List<TableItem?>,
    viewModel: HomeViewModel,
    navController: NavController
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 26.sp, modifier = Modifier.padding(start = 15.dp))

        Button(onClick = { viewModel.deleteTables() }) {

        }
    }

    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .height(height)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .background(Color.White),
        color = Color.White,
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Space(height = 10)
                Button(
                    onClick = {
                        viewModel.addTable()
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
            }

            items(tables) { tableItem ->
                Space(height = 10)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            brush = Brush.linearGradient(tableItem?.colors!!.checkColor()),
                            alpha = 0.7f
                        )
                        .clickable { navController.navigate("${BottomNavScreen.Table.route}/${tableItem.tableId}") }

                )
            }
        }
    }
}

fun List<String>.checkColor(): List<Color> {
    return this.map { Color(android.graphics.Color.parseColor(it)) }
}

private fun calculateCardHeight(tableCount: Int): Dp {
    val tableHeight = 100.dp
    val space = 10.dp

    val defaultHeight = 350.dp

    return if (tableCount <= 2) {
        defaultHeight
    } else {
        defaultHeight + (tableCount - 2) * (tableHeight + space)
    }
}