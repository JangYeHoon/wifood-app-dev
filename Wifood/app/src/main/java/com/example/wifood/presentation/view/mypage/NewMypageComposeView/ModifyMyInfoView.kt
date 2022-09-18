package com.example.wifood.presentation.view.mypage.NewMypageComposeView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.component.versionInfoField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyMyInfoView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
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
                navController.navigateUp()
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
                    navController.navigate(Route.ModifyPhoneNumber.route)
                }
            )
            CommonTextButton(
                text = "내 동네 변경",
                withButton = true,
                onClick = {
                    navController.navigate(Route.ModifyAddress.route)
                }
            )
            CommonTextButton(
                text = "내 입맛 수정",
                withButton = true,
                onClick = {
                    navController.navigate(Route.ModifyFavor.route)
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
