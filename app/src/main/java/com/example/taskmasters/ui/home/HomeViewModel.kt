package com.example.taskmasters.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmasters.data.TableItem
import com.example.taskmasters.data.TaskRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val tables: MutableState<List<TableItem>> = mutableStateOf(emptyList())

    private val taskRepository = TaskRepository()


    fun getTables(userId: String) {
        viewModelScope.launch {
            taskRepository.getTable(userId) { data ->
                tables.value = data
            }
        }
    }
//    fun loadTables(UserId: String) {
//        taskRepository.getTable(UserId) { tableData ->
//            tables.value = tableData
//        }
//    }

    fun saveTable() {
        taskRepository.saveTables(tables.value)
    }

    fun addTable(tableItem: TableItem) {
        taskRepository.addTable(tableItem)
    }
}