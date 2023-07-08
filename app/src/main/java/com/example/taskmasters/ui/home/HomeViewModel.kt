package com.example.taskmasters.ui.home

import androidx.compose.ui.graphics.Color
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

    fun addTable(color: Color) {
        viewModelScope.launch {
            taskRepository.addTable(TableItem(user.uid, color))
        }
    }
}