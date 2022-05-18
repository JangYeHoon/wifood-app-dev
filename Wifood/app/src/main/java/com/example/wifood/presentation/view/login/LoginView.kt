package com.example.wifood.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.PrimaryTextButton
import com.example.wifood.presentation.view.component.StandardTextField
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Error
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    navController.navigate(Route.Map.route)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_login_logo),
                contentDescription = "login Logo",
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(28.dp)
                    .width(86.dp)
            )
            Spacer(modifier = Modifier.height(39.dp))
            StandardTextField(
                label = "아이디",
                text = state.email,
                placeholder = "아이디를 입력해주세요.",
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                keyboardType = Email,
                isError = state.emailError != null
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = Error
                )
            }
            StandardTextField(
                label = "비밀번호",
                text = state.password,
                placeholder = "비밀번호를 입력해주세요.",
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                keyboardType = Password,
                isError = state.passwordError != null
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = Error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryTextButton(
                text = "로그인",
                onClick = {
                    viewModel.onEvent(LoginFormEvent.Login)
                }
            )
        }
    }
}