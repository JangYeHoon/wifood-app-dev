package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.component.YOGORadioButton
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.util.composableActivityViewModel

@Composable
fun SignUpView5(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            TitleText(
                text = "성별을 알려주세요.",
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                YOGORadioButton(
                    text = "남성",
                    onClick = {
                        viewModel.onEvent(SignUpEvent.GenderClicked)
                    },
                    selected = state.gender
                )
                YOGORadioButton(
                    onClick = {
                        viewModel.onEvent(SignUpEvent.GenderClicked)
                    },
                    selected = !state.gender
                )
                Spacer(modifier = Modifier.height(20.dp))
                MainButton(
                    text = "다음",
                    onClick = {
                        navController.navigate(Route.GetUserFavor.route)
                    }
                )
            }
        }
    }
}