package com.example.taskmasters.ui.auth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class Screen(val route: String) {
    object RegistrationScreet : Screen("reg_screen")
    object LogInScreen : Screen("log_screen")
}

@Composable
fun rememberUserAuth(): UserAuth = remember { UserAuth() }

class UserAuth(
    private val auth: FirebaseAuth = Firebase.auth
) {

    var userState by mutableStateOf(checkUser())
        private set

    private fun checkUser(): Boolean {
        val user = auth.currentUser
        return user != null
    }

    fun authWithEmail(email: String, password: String, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    userState = checkUser()
                } else {
                    val errorMessage = task.exception
                    if (errorMessage is FirebaseAuthUserCollisionException) {
                        onError("Данный адрес уже зарегистрирован")
                    } else {
                        onError("Ошибка регистрации")
                    }
                }
            }
    }

    fun signWithEmail(email: String, password: String, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    userState = checkUser()
                } else {
                    val errorMessage = task.exception
                    if (errorMessage is FirebaseAuthUserCollisionException) {
                        onError("Данный адрес уже зарегистрирован")
                    } else {
                        onError("Неверная почта или пароль")
                    }
                }
            }
    }

    fun authWithGoogle(tokenId: String, onError: (String) -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(tokenId, null)
        Firebase.auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    userState = checkUser()
                } else {
                    val exception = task.exception
                    Log.d("Google auth error: ", exception.toString())
                    onError("ошибка входа")
                }
            }
    }
}