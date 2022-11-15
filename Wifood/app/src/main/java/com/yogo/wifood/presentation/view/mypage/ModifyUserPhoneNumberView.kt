package com.yogo.wifood.presentation.view.mypage

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.login.util.ViewItem
import com.yogo.wifood.presentation.view.mypage.contents.ModifyUserPhoneNumberContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyUserPhoneNumberView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var done by remember { mutableStateOf(false) }

    LaunchedEffect(state.phoneNumber) {
        viewModel.checkForm()
    }

    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    done = true
                }
            }
        }
    }

    ModifyUserPhoneNumberContent(
        phoneNumberText = state.phoneNumber,
        onPhoneNumberValueChanged = {
            viewModel.onEvent(MyPageEvent.PhoneNumChanged(it))
        },
        onChangeButtonClicked = {
            viewModel.onEvent(MyPageEvent.ModifyUserInfo("PHONE"))
        },
        done
    )
}