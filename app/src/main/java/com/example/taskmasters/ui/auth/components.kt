@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.taskmasters.ui.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmasters.R
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@Composable
fun ContinueButton(
    isEmailFilled: Boolean,
    isUsernameFilled: Boolean,
    isPasswordFilled: Boolean,
    title: String,
    auth: () -> Unit
) {
    val isButtonEnabled = isEmailFilled && isUsernameFilled && isPasswordFilled

    Button(
        onClick = { auth() },
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.LightGray,
            containerColor = Color(0, 191, 50, 255),
            contentColor = Color.DarkGray
        ),
        enabled = isButtonEnabled,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clip(shape = RoundedCornerShape(50.dp))
            .size(60.dp)
    ) {
        Text(text = title, fontSize = 20.sp)
    }
}

@Composable
fun TextFields(
    Icon: ImageVector,
    title: String,
    type: KeyboardType,
    State: MutableState<Boolean>,
    import: MutableState<String>
) {
    OutlinedTextField(
        value = import.value,
        onValueChange = { newValue ->
            import.value = newValue
            State.value = !hasErrors(newValue, type)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = type),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Color.Gray,
            containerColor = Color.White,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.DarkGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Gray,
            errorBorderColor = Color.Red,
            unfocusedSupportingTextColor = Color.Red
        ),
        textStyle = TextStyle(fontSize = 24.sp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.White),
        leadingIcon = {
            Icon(
                Icon,
                contentDescription = "$title input"
            )
        },
        label = { Text(text = title, fontSize = 24.sp) },
        supportingText = {
            if (!State.value && !import.value.isBlank()) {
                if (type == KeyboardType.Email) {
                    if (!import.value.isValidEmail()) {
                        Text(
                            "Некорректный формат email",
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                } else if (type == KeyboardType.Password) {
                    if (!import.value.isValidPassword()) {
                        Text(
                            "Пароль должен содержать цифры и буквы, и быть не менее 8 символов",
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        },
    )
    Space(5)
}

private fun hasErrors(value: String, type: KeyboardType): Boolean {
    return when (type) {
        KeyboardType.Email -> !value.isValidEmail()
        KeyboardType.Password -> !value.isValidPassword()
        else -> false
    }
}

@Composable
fun SignBlock() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleButton()
    }
}

@Composable
fun GoogleButton(
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state = rememberOneTapSignInState()
    val scope = rememberCoroutineScope()

    OneTapSignInWithGoogle(
        state = state,
        clientId = "761394542856-9c6a7l7abtpmlptf0mqfh113te7aumq7.apps.googleusercontent.com",
        onTokenIdReceived = { tokenId ->
            scope.launch {
                viewModel.googleLogin(tokenId)
            }
        },
        onDialogDismissed = { message ->
            Log.d("LOG", message)
        }
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { state.open() }
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp
                    )
                )
                .border(
                    1.dp,
                    Color.Black,
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp
                    )
                )
                .background(
                    Color.White,
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp
                    )
                )
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.google_logo),
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds
            )
        }
        Box(
            modifier = Modifier
                .size(height = 70.dp, width = 200.dp)
                .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                .border(
                    1.dp,
                    Color.Black,
                    RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                )
                .background(Color.White, RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
        ) {
            Text(
                text = "С помощью Google",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun Space(height: Int) {
    val dp = height.dp
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun Or(navController: NavController, title: String, screen: Screen) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .padding(start = 5.dp)
        )
        TextButton(onClick = { navController.navigate(screen.route) }) {
            Text(text = title, fontSize = 16.sp, color = Color.Gray)
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .padding(end = 5.dp)
        )
    }
}


private fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return matches(emailRegex)
}

private fun String.isValidPassword(): Boolean {
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    return matches(passwordRegex)
}