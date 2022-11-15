package com.yogo.wifood.presentation.view.login

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.contents.GetUserAgreementContent
import com.yogo.wifood.presentation.view.login.new_compose_views.GetUserAgreementDetailView
import com.yogo.wifood.util.composableActivityViewModel

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetUserAgreementView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value
    val showPersonalAgreementDialog = remember { mutableStateOf(false) }

    GetUserAgreementContent(
        onAgreeClicked = {
            viewModel.onEvent(SignUpEvent.AgreementClicked)
        },
        agreeChecked = state.agreement,
        showAgreementDialog = showPersonalAgreementDialog,
        onButtonClicked = {
            navController.navigate(Route.SignUp3.route)
        }
    )
    if (showPersonalAgreementDialog.value)
        GetUserAgreementDetailView(viewModel, showPersonalAgreementDialog)
}