package com.example.wifood.presentation.view.mypage.contents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOBasicText
import com.example.wifood.presentation.view.login.ClickableTextFieldForm1
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserLocationView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState() // for horizontal mode screen
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ){
            Spacer(Modifier.weight(1f))
            YOGOBasicText(
                largeText = "새로운 동네를\n알려주세요.",
                explainText = "동, 읍까지만 입력해주세요."
            )
            Spacer(Modifier.height(24.dp))
            ClickableTextFieldForm1(
                text = state.address,
                onClick = {
                    navController.navigate(Route.FindLocation.route)
                },
                onValueChange = {
                    viewModel.onEvent(MyPageEvent.AddressChanged(it))
                }
            )
            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "변경하기",
                onClick = {
                    viewModel.onEvent(MyPageEvent.ModifyUserInfo("ADDRESS"))
                },
                activate = MainData.user.address.isNotEmpty()
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}