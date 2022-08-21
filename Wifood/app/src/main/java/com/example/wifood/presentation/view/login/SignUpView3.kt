package com.example.wifood.presentation.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.util.composableActivityViewModel

@Composable
fun SignUpView3(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            TitleText(
                text = "동네를 알려주세요.",
                color = Color.Black
            )
            BasicTextField(
                value = state.address,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.AddressChanged(it))
                }
            )
            MainButton(
                text = "다음",
                onClick = {
                    viewModel.onEvent(SignUpEvent.ButtonClicked)
                }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.searchResults) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .clickable {
                                viewModel.onEvent(SignUpEvent.AddressClicked(it.fullAddress))

                                navController.navigate(Route.SignUp4.route)
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Row {
                                Text(text = it.name, fontSize = 16.sp)
                                Text(text = it.bizName)
                            }
                            Text(text = it.fullAddress)
                        }
                    }
                }
            }
        }
    }
}