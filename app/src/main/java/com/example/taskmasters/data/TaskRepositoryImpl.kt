package com.example.taskmasters.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow



class TaskRepositoryImpl(private val database: FirebaseFirestore = Firebase.firestore) :
    TaskRepository {

    override fun addTable(tableItem: TableItem) {
        database.collection("tables")
            .document()
            .set(tableItem.copy())
    }


    override fun getTables(user: FirebaseUser): Flow<List<TableItem>> {
        return callbackFlow {
            val tablesRef = database.collection("tables")
                .whereEqualTo("userId", user.uid)

            val listenerRegistration = tablesRef.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }

                val tables = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(TableItem::class.java)
                } ?: emptyList()

                trySend(tables).isSuccess
            }

            awaitClose { listenerRegistration.remove() }
        }
    }
}

data class TableItem(
    val userId: String,
    val color: String
) {
    
    constructor(): this("", "#0894ld")
}

data class GradientWrapper(
    val colors: List<Int>,
    val positions: List<Float>,
    val orientation: Orientation
) {
    enum class Orientation {
        Horizontal,
        Vertical
    }
}
