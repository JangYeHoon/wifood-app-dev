package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.Picker
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.presentation.view.login.util.phoneFilter
import com.example.wifood.util.composableActivityViewModel

@Composable
fun SignUpView4(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                TitleText(
                    text = "생일을 알려주세요.",
                    color = Color.Black
                )
                BasicTextField(
                    value = state.birthday,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.CertChanged(it))
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Picker(
                        state = remember { mutableStateOf(1995) },
                        range = 1950..2022
                    )
                    Picker(
                        state = remember { mutableStateOf(1) },
                        range = 1..12
                    )
                    Picker(
                        state = remember { mutableStateOf(10) },
                        range = 1..31
                    )
                }
                MainButton(text = "다음", onClick = { navController.navigate(Route.SignUp5.route) })
            }
        }
    }
}