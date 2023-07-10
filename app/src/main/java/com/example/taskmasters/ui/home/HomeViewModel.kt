package com.example.taskmasters.ui.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmasters.data.TableItem
import com.example.taskmasters.data.TaskRepositoryImpl
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(
    private val taskRepository: TaskRepositoryImpl = TaskRepositoryImpl(),
    private val user: FirebaseUser = Firebase.auth.currentUser!!
) : ViewModel() {

    private val _tables = MutableStateFlow<List<TableItem?>>(emptyList())
    val tables: StateFlow<List<TableItem?>> = _tables

    fun loadTables() {
        viewModelScope.launch {
            taskRepository.getTables(user)
                .collect { tables ->
                    _tables.value = tables
                }
        }
    }

    fun addTable(color: List<String> = generateSmoothGradient()) {
        viewModelScope.launch {
            taskRepository.addTable(TableItem(user.uid, color))
        }
    }

    private fun generateSmoothGradient(): List<String> {
        val colors = mutableListOf<String>()
        val baseColor = generateSoftColor().toArgb()

        repeat((2..3).random()) {
            val variation = Random.nextInt()
            val color = Color(baseColor + variation).toArgb()
            colors.add(String.format("#%06x", (0xFFFFFF and color)))
        }

        return colors
    }


    private fun generateSoftColor(): Color {
        val r = Random.nextInt()
        val g = Random.nextInt()
        val b = Random.nextInt()
        val a = Random.nextInt()

        return Color(r, g, b, a)
    }
}