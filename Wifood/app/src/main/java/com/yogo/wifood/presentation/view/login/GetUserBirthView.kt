 package com.yogo.wifood.presentation.view.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.contents.GetUserBirthContent
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.util.composableActivityViewModel

@Composable
fun GetUserBirthView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {

    val year = remember { mutableStateOf(1995) }
    val month = remember { mutableStateOf(1) }
    val day = remember { mutableStateOf(10) }

    GetUserBirthContent(
        year = year,
        month = month,
        day = day,
        onButtonClicked = {
            SignUpData.birthday = buildString {
                append(year.value)
                append(month.value)
                append(day.value)
            }
            navController.navigate(Route.SignUp5.route)
        }
    )
}
