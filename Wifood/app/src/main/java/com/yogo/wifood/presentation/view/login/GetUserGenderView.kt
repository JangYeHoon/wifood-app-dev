package com.yogo.wifood.presentation.view.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.contents.GetUserGenderContent
import com.yogo.wifood.util.composableActivityViewModel

@Composable
fun GetUserGenderView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    GetUserGenderContent(
        onGenderClicked  = {
            viewModel.onEvent(SignUpEvent.GenderClicked)
        },
        gender = state.gender,
        onNextButtonClicked = {
            navController.navigate(Route.GetUserFavor.route)
        }
    )
}