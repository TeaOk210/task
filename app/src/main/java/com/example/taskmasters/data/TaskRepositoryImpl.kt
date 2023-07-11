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

    override fun deleteAllTables(user: FirebaseUser) {
        val ref = database.collection("tables")
            .whereEqualTo("userId", user.uid)

        ref.get().addOnSuccessListener { querySnapshot ->
            val batch = database.batch()
            querySnapshot.documents.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit()
        }.addOnFailureListener { exception ->
            // Handle failure
        }
    }

    override fun getTable(user: FirebaseUser, tableId: Int): Flow<TableItem> {
        return callbackFlow {
            val tableRef = database.collection("tables")
                .whereEqualTo("userId", user.uid)
                .whereEqualTo("tableId", tableId)
                .limit(1)

            tableRef.get().addOnSuccessListener { querySnapshot ->
                val table = querySnapshot.documents.firstOrNull()?.toObject(TableItem::class.java)
                table?.let { trySend(it) }
            }.addOnFailureListener { exception ->
                close(exception)
            }

            awaitClose {}
        }
    }
}

data class TableItem(
    val userId: String,
    val colors: List<String>,
    val tableId: Int
) {
    constructor() : this("", listOf("#b0e0e6", "#b9f2ff"), 1)
}