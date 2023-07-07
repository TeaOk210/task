package com.example.taskmasters.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmasters.data.AuthRepository
import com.example.taskmasters.data.AuthRepositoryImpl
import com.example.taskmasters.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {
    private val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }

                is Resource.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }

                is Resource.Success -> {
                    _authState.send(AuthState(isSuccess = "Успешно"))
                }
            }
        }
    }

    fun registrUser(email: String, password: String) = viewModelScope.launch {
        repository.registrUser(email, password).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }

                is Resource.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }

                is Resource.Success -> {
                    _authState.send(AuthState(isSuccess = "Успешно"))
                }
            }
        }
    }

    fun googleLogin(tokenId: String) = viewModelScope.launch {
        repository.googleLogin(tokenId).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }

                is Resource.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }

                is Resource.Success -> {
                    _authState.send(AuthState(isSuccess = "Успешно"))
                }
            }
        }
    }
}