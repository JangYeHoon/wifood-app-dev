package com.yogo.wifood.presentation.view.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.contents.JoininCompleteContent
import kotlinx.coroutines.delay

@Composable
fun JoininCompleteView(
    navController: NavController,
) {
    LaunchedEffect(true) {
        delay(1500L)
        navController.navigate(Route.Main.route)
    }
    JoininCompleteContent()

}

