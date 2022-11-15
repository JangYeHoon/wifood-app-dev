package com.yogo.wifood.presentation.view.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.presentation.view.mypage.contents.ModifyUserLocationContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyUserLocationView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
){
    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigateUp()
                }
            }
        }
    }

    ModifyUserLocationContent(
        onTextFieldClicked = {
            navController.navigate("${Route.FindLocation.route}/modify")
        },
        onTextFieldValueChanged = {
            viewModel.onEvent(MyPageEvent.AddressChanged(it))
        },
        onModifyButtonClicked = {
            viewModel.onEvent(MyPageEvent.ModifyUserInfo("ADDRESS"))
        },
        activateButton = MainData.user.address.isNotEmpty()
    )
}