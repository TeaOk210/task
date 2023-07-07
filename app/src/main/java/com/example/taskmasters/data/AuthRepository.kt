package com.example.taskmasters.data

import com.example.taskmasters.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registrUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun googleLogin(tokenId: String): Flow<Resource<AuthResult>>
}