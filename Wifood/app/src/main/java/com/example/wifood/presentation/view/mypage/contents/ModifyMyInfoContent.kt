package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyMyInfoContent(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel(),
    onBackButtonClicked: () -> Unit = {},
    onModifyPhoneNumberClicked: () -> Unit = {},
    onModifyUserLocationClicked: () -> Unit = {},
    onModifyUserFavorClicked: () -> Unit = {},
    onWithdrawClicked: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Route.GetPhoneNumber.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "내 정보 수정",
            leftButtonOn = true,
            leftButtonClicked = {
                onBackButtonClicked()
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            CommonTextButton(
                text = "휴대폰 번호 변경",
                withButton = true,
                onClick = {
                    onModifyPhoneNumberClicked()
                }
            )
            CommonTextButton(
                text = "내 동네 변경",
                withButton = true,
                onClick = {
                    onModifyUserLocationClicked()
                }
            )
            CommonTextButton(
                text = "내 입맛 수정",
                withButton = true,
                onClick = {
                    onModifyUserFavorClicked()
                }
            )
            CommonTextButton(
                text = "회원탈퇴",
                withButton = false,
                onClick = {
                    viewModel.onEvent(MyPageEvent.DeleteUser)
                }
            )
        }
    }
}
