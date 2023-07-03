package com.example.taskmasters.data

import androidx.compose.ui.graphics.Color
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TaskRepository {
    private val database = Firebase.database
    private val TableRef = database.getReference("Tables")

    fun getTable(UserId: String, callback: (List<TableItem>) -> Unit) {
        TableRef.child(UserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tableList = mutableListOf<TableItem>()
                for (childSnapshot in snapshot.children) {
                    val tableItem = childSnapshot.getValue(TableItem::class.java)
                    tableItem?.let { tableList.add(it) }
                }
                callback(tableList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun saveTables(tableItems: List<TableItem>) {
        TableRef.setValue(tableItems)
    }

    fun addTable(tableItem: TableItem) {
        val tableId = TableRef.push().key
        tableId?.let {
            TableRef.child(it).setValue(tableItem)
        }
    }
}

data class TableItem(
    val UserId: String,
    val color: Color
)