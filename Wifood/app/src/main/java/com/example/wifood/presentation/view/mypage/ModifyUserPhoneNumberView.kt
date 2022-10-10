package com.example.wifood.presentation.view.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.mypage.contents.ModifyUserPhoneNumberContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyUserPhoneNumberView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigateUp()
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
        }
    )
}