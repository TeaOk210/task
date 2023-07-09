package com.example.taskmasters.ui.home

import androidx.core.graphics.ColorUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmasters.data.GradientWrapper
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

    fun addTable(color: GradientWrapper = generateSmoothGradient()) {
        viewModelScope.launch {
            taskRepository.addTable(TableItem(user.uid, color))
        }
    }

    private fun generateSmoothGradient(): GradientWrapper {
        val numColors = (2..4).random()
        val colors = mutableListOf<String>()
        val positions = mutableListOf<Float>()

        val hueStep = 360f / numColors
        var currentHue = 0f

        repeat(numColors) {
            val color = ColorUtils.HSLToColor(floatArrayOf(currentHue, 0.5f, 0.5f))
            colors.add(String.format("#%06X", 0xFFFFFF and color))

            val position = it.toFloat() / (numColors - 1)
            positions.add(position)

            currentHue += hueStep
        }

        val orientation = if (Random.nextBoolean()) {
            GradientWrapper.Orientation.Horizontal
        } else {
            GradientWrapper.Orientation.Vertical
        }

        return GradientWrapper(colors, positions, orientation)
    }

}