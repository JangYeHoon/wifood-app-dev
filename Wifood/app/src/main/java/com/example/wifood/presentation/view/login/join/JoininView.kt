package com.example.wifood.presentation.view.login.join

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.component.navigationBackButton
import com.example.wifood.presentation.view.login.component.*
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.ui.theme.fontRoboto
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun JoininView(
    navController: NavController,
    viewModel: JoininViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigateUp()
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "회원가입",
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(horizontal = sidePaddingValue.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(31.dp))
            // set id
            TitleText("아이디")
            Spacer(Modifier.height(5.dp))
            ExplainText("영문, 숫자를 포함한 아이디를 입력해주세요")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = formState.email,
                placeholder = "아이디",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.EmailChanged(it))
                    }
                }
            )
            ErrorText(
                text = "**이미 사용중인 아이디입니다",
                visibility = true,
            )
            Spacer(Modifier.height(20.dp))

            // Set password
            TitleText("비밀번호")
            Spacer(Modifier.height(5.dp))
            ExplainText("영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = formState.password,
                placeholder = "비밀번호",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.PasswordChanged(it))
                    }
                }
            )
            ErrorText(
                text = "**올바른 비밀번호를 설정해주세요",
                visibility = true,
            )
            // Set password check
            InputTextField(
                text = formState.repeatedPassword,
                placeholder = "비밀번호 확인",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.RepeatedPasswordChanged(it))
                    }
                }
            )
            ErrorText(
                text = "**비밀번호가 일치하지 않습니다.",
                visibility = true,
            )
            Spacer(Modifier.height(20.dp))

            // Set nickname
            TitleText("닉네임")
            Spacer(Modifier.height(5.dp))
            ExplainText("다른 유저와 겹치지 않는 닉네임을 입력해주세요 (2~15자)")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = formState.nickname,
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.NicknameChanged(it))
                    }
                },
                placeholder = "닉네임 (2~15자)"
            )
            ErrorText(
                text = "**이미 사용중인 닉네임입니다",
                visibility = true,
            )
            Spacer(Modifier.height(20.dp))

            // Set address
            TitleText("주소")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = formState.address,
                placeholder = "주소 검색",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.AddressChanged(it))
                    }
                },
            )
            ErrorText(
                text = "**주소를 입력해주세요",
                visibility = true,
            )
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.fillMaxWidth(0.6f)
                ) {
                    Column() {
                        TitleText("생년월일")
                        Spacer(Modifier.height(5.dp))
                        InputTextField(
                            text = "",
                            placeholder = "YYMMDD",
                            onValueChange = {
                            },
                        )
                    }
                }
                Spacer(Modifier.width(10.dp))
                Box(Modifier.fillMaxWidth(1f)) {
                    Column() {
                        TitleText("성별")
                        Spacer(Modifier.height(10.dp))
                        Row() {
                            YOGORadioButton(
                                text = "남성",
                                onClick = {},
                                selected = true
                            )
                            Spacer(Modifier.width(10.dp))
                            YOGORadioButton(
                                text = "여성",
                                onClick = {},
                                selected = false
                            )
                            Spacer(Modifier.width(10.dp))
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
            ErrorText(
                text = "**생년월일을 올바르게 입력해주세요",
                visibility = true,
            )
            Spacer(Modifier.height(28.dp))
            /*Divider(
                color = DividerColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
            )*/
            Spacer(Modifier.height(30.dp))
            TitleText("약관동의")
            Spacer(Modifier.height(16.dp))
            Row()
            {
                ToggleButton(
                    onClick = {
                        scope.launch {
                            viewModel.onEvent(JoininEvent.TermsClicked)
                        }
                    },
                    isChecked = formState.terms
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "개인정보수집 및 이용동의",
                    color = Gray01Color,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "(필수)",
                    color = MainColor,
                    modifier = Modifier.padding(top = 4.dp),
                    fontFamily = fontRoboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "수집목적 : 정보를 이용한 내용 내용 내용 내용 내용\n보유 및 이용기간 : 회원가입 철회 시 까지",
                Modifier.padding(start = 24.dp),
                color = Gray03Color,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
            )
            ErrorText(
                text = "   **개인정보를 동의해주세요",
                visibility = true,
            )
            Spacer(Modifier.height(48.dp))

            Row(
                Modifier.align(Alignment.CenterHorizontally)
            ) {
                TransparentButton(
                    text = "정보 더 입력하고 자세한 추천 받기 >",
                    textColor = MainColor,
                    textSize = 12,
                    onClick = {/*TODO*/ }
                )
            }
            Spacer(Modifier.height(17.dp))
            MainButton(
                text = "회원가입하기",
                onClick = {
                    scope.launch {
                        viewModel.onEvent(JoininEvent.Joinin)
                    }
                }
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}

@Composable
fun test() {
    var isGenderMale: Boolean = false
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.fillMaxWidth(0.65f)
        ) {
            Column() {
                TitleText("생년월일")
                Spacer(Modifier.height(5.dp))
                InputTextField(
                    text = "",
                    placeholder = "YYMMDD",
                    onValueChange = {
                    },
                )
            }
        }
        Spacer(Modifier.width(10.dp))
        Box(Modifier.fillMaxWidth(1f)) {
            Column() {
                TitleText("성별")
                Spacer(Modifier.height(10.dp))
                Row() {
                    YOGORadioButton(
                        text = "남성",
                        onClick = {},
                        selected = true
                    )
                    Spacer(Modifier.width(10.dp))
                    YOGORadioButton(
                        text = "여성",
                        onClick = {},
                        selected = false
                    )
                    Spacer(Modifier.width(10.dp))
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}