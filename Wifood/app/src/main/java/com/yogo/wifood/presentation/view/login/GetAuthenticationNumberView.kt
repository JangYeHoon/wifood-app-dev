package com.yogo.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.login.SignUpViewModel
import com.yogo.wifood.presentation.view.login.contents.GetAuthenticationNumberContent
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.util.composableActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetAuthenticationNumberView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    var timer by remember { mutableStateOf(150) }

    DisposableEffect(true) {
        viewModel.onEvent(SignUpEvent.RequestCertNumber)

        onDispose {

        }
    }

    LaunchedEffect(timer) {
        for (i in timer downTo 1) {
            delay(1000L)
            timer--
        }
    }

    LaunchedEffect(state.certNumber) {
        if (state.certNumber.length == 4) {
            viewModel.onEvent(SignUpEvent.Verify(state.certNumber, timer))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (SignUpData.exist) {
                        com.yogo.wifood.WifoodApp.pref.setString("Initial_Flag", "1")
                        navController.navigate(Route.Main.route)
                    } else {
                        navController.navigate(Route.Agreement.route)
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        GetAuthenticationNumberContent(
            isLoading = state.isLoading,
            timeText = toTimer(timer),
            checkAuthNumber = {
                viewModel.onEvent(SignUpEvent.CertChanged(it))
            },
            onButtonClicked = {
                timer = 150
                viewModel.onEvent(SignUpEvent.RequestCertNumber)
            }
        )
    }
}

private fun toTimer(time: Int): String {
    return buildString {
        append(time / 60)
        append(":")
        if (time % 60 < 10) append("0")
        append(time % 60)
    }
}