package com.example.taskmasters.data

import com.example.taskmasters.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val deferred = CompletableDeferred<Resource<AuthResult>>()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        deferred.complete(Resource.Success(result))
                    } else {
                        val exception = task.exception
                        val errorMessage = exception?.message ?: "Неизвестная ошибка"
                        when (exception) {
                            is FirebaseAuthInvalidUserException -> {
                                deferred.complete(Resource.Error("Пользователь не найден или удален"))
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                deferred.complete(Resource.Error("Неправильный пароль"))
                            }

                            is FirebaseAuthUserCollisionException -> {
                                deferred.complete(Resource.Error("Учетная запись уже используется"))
                            }

                            else -> {
                                deferred.complete(Resource.Error(errorMessage))
                            }
                        }
                    }
                }
            try {
                emit(deferred.await())
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }


    override fun registrUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val deferred = CompletableDeferred<Resource<AuthResult>>()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        deferred.complete(Resource.Success(result))
                    } else {
                        val errorMessage = task.exception?.message ?: "Неизвестная ошибка"
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            deferred.complete(Resource.Error("Данный адрес уже зарегестрирован"))
                        } else {
                            deferred.complete(Resource.Error(errorMessage))
                        }
                    }
                }
            try {
                emit(deferred.await())
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun googleLogin(tokenId: String): Flow<Resource<AuthResult>> {
        val firebaseCredential = GoogleAuthProvider.getCredential(tokenId, null)
        return flow {
            emit(Resource.Loading())
            val deferred = CompletableDeferred<Resource<AuthResult>>()
            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        deferred.complete(Resource.Success(result))
                    } else {
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        deferred.complete(Resource.Error(errorMessage))
                    }
                }
            try {
                emit(deferred.await())
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}