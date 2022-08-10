package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.login.util.ViewItem
import com.example.wifood.presentation.view.login.util.phoneFilter

@Composable
fun SignUpView1(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(state.phoneNumber) {
        val success = mutableStateOf(false)

        if (state.phoneNumber.length == 11) {
            success.value = viewModel.checkForm(ViewItem.SignUpView1)
        }

        if (success.value) {
            navController.navigate("${Route.SignUp2.route}/${state.phoneNumber}")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = state.phoneNumber,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.PhoneNumChanged(it))
            },
            visualTransformation = {
                phoneFilter(it)
            }
        )
    }
}

