package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.login.util.phoneFilter

@Composable
fun SignUpView3(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.AddressChanged(it))
            }
        )
    }
}