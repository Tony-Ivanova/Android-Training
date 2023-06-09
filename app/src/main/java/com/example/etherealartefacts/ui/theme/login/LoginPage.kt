package com.example.etherealartefacts.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.response.LoginRequest
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.ui.theme.ColorPalette
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.loginButton
import com.example.etherealartefacts.ui.theme.loginInputs
import com.example.etherealartefacts.ui.theme.loginLabel
import com.example.etherealartefacts.ui.theme.utils.showToastMessage

@Composable
fun LoginPage(jwtTokenProvider: JWTTokenProvider, navController: NavHostController) {
    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = hiltViewModel()
    val isLoadingState = viewModel.isLoading.collectAsState()
    val isLoading = isLoadingState.value
    val notEmptyInputs = stringResource(id = R.string.not_empty)
    val loginResult = viewModel.loginResult.collectAsState().value
    val shouldBeEnabled = remember { mutableStateOf(true) }

    LaunchedEffect(loginResult) {
        if (loginResult is LoginResult.LoginError) {
            val errorMessage = loginResult.errorMessage
            shouldBeEnabled.value = false
            showToastMessage(context, errorMessage)
        } else if (loginResult is LoginResult.LoginSuccess) {
            val jwtToken = loginResult.jwtToken
            jwtTokenProvider.setJwtToken(jwtToken)
            navController.navigate("home")
        }
    }

    Box() {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.login_background),
            contentDescription = stringResource(id = R.string.background_loginP_cd),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.padding()
        ) {
            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.logo_pt),
                    bottom = dimensionResource(id = R.dimen.logo_pb),
                    start = dimensionResource(id = R.dimen.logo_px),
                    end = dimensionResource(id = R.dimen.logo_px)
                )
            ) {
                Image(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.logo_width))
                        .height(dimensionResource(id = R.dimen.logo_height)),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(id = R.string.logo_cd)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.loginInput_px),
                        end = dimensionResource(id = R.dimen.loginInput_px),
                        bottom = dimensionResource(id = R.dimen.loginInput_py)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(
                        bottom = dimensionResource(id = R.dimen.loginLabel_pb)
                    ),
                    text = stringResource(id = R.string.login_label),
                    style = Typography.loginLabel
                )

                OutlinedTextField(
                    modifier = Modifier.width(dimensionResource(id = R.dimen.loginInput_width)),
                    value = email.value,
                    onValueChange = { newEmailValue -> email.value = newEmailValue },
                    label = {
                        Text(
                            text = stringResource(id = R.string.email_input),
                            style = Typography.loginInputs(emailError.value.isNotEmpty()),
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.email_input),
                            style = Typography.loginInputs(emailError.value.isNotEmpty()),
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        errorBorderColor = if (emailError.value.isNotEmpty()) ColorPalette.Red.BrickRed else ColorPalette.BW.DarkGray
                    ),
                )

                if (emailError.value.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.errorMessage_pl)
                        ), text = emailError.value, color = ColorPalette.Red.BrickRed
                    )
                }


                OutlinedTextField(value = password.value,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.loginInput_py))
                        .width(dimensionResource(id = R.dimen.loginInput_width)),
                    onValueChange = { newPasswordValue: String ->
                        password.value = newPasswordValue
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.password_input),
                            style = Typography.loginInputs(passwordError.value.isNotEmpty()),
                        )
                    },
                    placeholder = {
                        Text(
                            stringResource(id = R.string.password_input),
                            style = Typography.loginInputs(passwordError.value.isNotEmpty()),
                        )
                    },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        errorBorderColor = if (passwordError.value.isNotEmpty()) ColorPalette.BW.DarkGray else ColorPalette.Red.BrickRed
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisibility.value) R.drawable.visibility else R.drawable.visibility_off
                                ),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.passwordIcon_size)),
                                contentDescription = if (passwordVisibility.value) stringResource(id = R.string.hide_password_cd) else stringResource(
                                    id = (R.string.show_password_cd)
                                )
                            )
                        }
                    })

                if (passwordError.value.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.errorMessage_pl)
                        ), text = passwordError.value, color = ColorPalette.Red.BrickRed
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Button(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.loginButton_height))
                        .width(dimensionResource(id = R.dimen.loginButton_width))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.loginButton_curve))),
                    onClick = {
                        if (email.value.isEmpty()) {
                            val emailErrorMessage = context.getString(R.string.email_invalid)
                            email.value = "\t"
                            emailError.value = emailErrorMessage
                            shouldBeEnabled.value = false
                        }
                        else if (password.value.isEmpty()) {
                            val passwordErrorMessage = context.getString(R.string.password_invalid)
                            password.value = "\t"
                            passwordError.value = passwordErrorMessage
                            shouldBeEnabled.value = false
                        }
                        else {
                            val loginRequest = LoginRequest(email.value, password.value)
                            viewModel.login(loginRequest)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorPalette.Purple.DarkIndigo,
                        disabledBackgroundColor = ColorPalette.BW.DisabledGray10
                    ),
                    enabled = !isLoading && shouldBeEnabled.value,

                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(
                                dimensionResource(id = R.dimen.loginButton_height)
                            ), color = ColorPalette.BW.White
                        )
                    } else if(!shouldBeEnabled.value){
                        Text(
                            text = stringResource(id = R.string.login_label),
                            color = ColorPalette.BW.DisabledGray
                        )
                    }
                    else {
                        Text(
                            text = stringResource(id = R.string.login_label),
                            style = Typography.loginButton
                        )
                    }
                }
            }
        }
    }
}