package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.phoneFilter

@Composable
fun SignUpView2(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    val state = viewModel.state.value

    LaunchedEffect(state.certNumber) {
        if (state.certNumber.length == 6) {
            viewModel.onEvent(SignUpEvent.Verify(state.certNumber))

            navController.navigate(Route.SignUp3.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            TitleText(
                text = state.phoneNumber
            )
            BasicTextField(
                value = state.certNumber,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.CertChanged(it))
                }
            )
        }
    }
}