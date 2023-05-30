package com.example.etherealartefacts.pages

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.etherealartefacts.viewModels.LoginViewModel
import com.example.etherealartefacts.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LoginPage(navController: NavHostController) {
    val context = LocalContext.current

    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (emailError, setEmailError) = remember { mutableStateOf("") }
    val (passwordError, setPasswordError) = remember { mutableStateOf("") }
    val (isPasswordFocused, setIsPasswordFocused) = remember { mutableStateOf(false) }
    val (isEmailFocused, setIsEmailFocused) = remember { mutableStateOf(false) }
    val (passwordVisibility, setPasswordVisibility) = remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = hiltViewModel()
    val (isLoading, setIsLoading) = remember { mutableStateOf(viewModel.isLoading.value) }
    val (loginError, setLoggingError) = remember { mutableStateOf(viewModel.loginError.value) }
    val (jwtToken, setJwtToken) = remember { mutableStateOf(viewModel.jwtToken.value) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(top = 50.dp)
                .size(120.dp),
        )

        Text(
            text = "ETHEREAL ARTEFACTS", style = MaterialTheme.typography.body1.copy(
                fontSize = 30.sp,
            ), modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Log in", style = MaterialTheme.typography.body1.copy(
                fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color(70, 51, 122)
            ), modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        )

        if (loginError.isNotEmpty()) {
            showToastMessage(context, loginError)
        } else if (jwtToken.isNotEmpty()) {
            LaunchedEffect(Unit) {
                Log.d("LoginPage", "LaunchedEffect triggered")
                navController.navigate("product")
            }
        }

        OutlinedTextField(value = email,
            onValueChange = { setEmail(it) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { focusState: FocusState ->
                    setIsEmailFocused(focusState.isFocused)
                })

        if (email.length < 4) {
            setEmailError("Email should be at least 4 characters long.")
        } else {
            setEmailError("")
        }

        if (emailError.isNotEmpty() && isEmailFocused) {
            Text(
                text = emailError, color = Color.Red, modifier = Modifier.padding(top = 4.dp)
            )
        }

        OutlinedTextField(value = password,
            onValueChange = { setPassword(it) },
            label = { Text("Password") },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { focusState: FocusState ->
                    setIsPasswordFocused(focusState.isFocused)
                },
            trailingIcon = {
                IconButton(onClick = { setPasswordVisibility(!passwordVisibility) }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisibility) R.drawable.visibility else R.drawable.visibility_off
                        ),
                        modifier = Modifier.size(25.dp),
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            })

        if (password.length < 6) {
            setPasswordError("Password should be at least 6 characters long.")
        } else {
            setPasswordError("")
        }

        if (passwordError.isNotEmpty() && isPasswordFocused) {
            Text(
                text = passwordError, color = Color.Red, modifier = Modifier.padding(top = 4.dp)
            )
        }

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    setLoggingError("Email or password cannot be empty.")
                }

                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        viewModel.getLoggedInUser(email, password)
                        setJwtToken(viewModel.jwtToken.value)
                    } catch (e: Exception) {
                        setLoggingError("Somewhere over the rainbow it's 01:00AM.")
                    } finally {
                        setIsLoading(viewModel.isLoading.value)
                    }
                }

                setIsPasswordFocused(false)
                setPasswordError("")
                setPassword("")
            },
            enabled = !isLoading,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(40.dp)
                .width(250.dp)
                .clip(RoundedCornerShape(30.dp)),
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                ),
            )
        }
    }
}

fun showToastMessage(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Log.d("Toast", message)
    Toast.makeText(context, message, duration).show()
}