package com.example.taskmasters.data

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun addTable(tableItem: TableItem)
    fun getTables(user: FirebaseUser): Flow<List<TableItem>>
    fun deleteAllTables(user: FirebaseUser)
}