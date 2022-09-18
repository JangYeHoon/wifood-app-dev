package com.example.wifood.presentation.view.mypage.NewMypageComposeView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.login.util.phoneFilter
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyUserPhoneNumberView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    var phoneNumberValidation by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
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
        ) {
            //TEST line

            Spacer(Modifier.weight(1f))
            Text(
                text = "휴대폰 번호를\n입력해주세요.",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(24.dp))
            TextField(
                value = state.phoneNumber,
                onValueChange = {
                    viewModel.onEvent(MyPageEvent.PhoneNumChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Gray01Color
                ),
                placeholder = {
                    Text(
                        text = "휴대폰번호 ('-'제외)",
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = EnableColor
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = MainColor,
                    textColor = Gray01Color,
                    placeholderColor = EnableColor,
                    focusedIndicatorColor = MainColor,
                    unfocusedIndicatorColor = EnableColor
                ),
                visualTransformation = {
                    phoneFilter(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "인증번호 받기",
                onClick = {
                    viewModel.onEvent(MyPageEvent.ModifyUserInfo("PHONE"))
                }
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }

    }
}