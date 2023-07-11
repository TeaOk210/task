package com.example.taskmasters.ui.table

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

class TableViewModel(
    private val taskRepository: TaskRepositoryImpl = TaskRepositoryImpl(),
    private val user: FirebaseUser = Firebase.auth.currentUser!!
) : ViewModel() {
    val _table = MutableStateFlow<TableItem>(TableItem())
    val table: StateFlow<TableItem> = _table


    fun loadTable(tableId: Int) {
        viewModelScope.launch {
            taskRepository.getTable(user, tableId)
                .collect { receivedTable ->
                    _table.value = receivedTable
                }
        }
    }
}