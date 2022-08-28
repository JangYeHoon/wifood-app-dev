package com.example.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetPhoneAuthenticationNumberView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    var timer by remember { mutableStateOf(150) }

    var first by remember { mutableStateOf("") }
    var second by remember { mutableStateOf("") }
    var third by remember { mutableStateOf("") }
    var fourth by remember { mutableStateOf("") }

    DisposableEffect(true) {
        viewModel.onEvent(SignUpEvent.RequestCertNumber)

        onDispose {

        }
    }

    LaunchedEffect(timer) {
        for (i in timer downTo 1) {
            delay(1000L)
            timer--
        }
    }

    LaunchedEffect(state.certNumber) {
        if (state.certNumber.length == 4) {
            viewModel.onEvent(SignUpEvent.Verify(state.certNumber, timer))

            navController.navigate(Route.Agreement.route)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (state.isLoading) {
            ProgressIndicator()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = sidePaddingValue.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "인증번호 4자리를\n입력해주세요.",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = toTimer(timer),
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MainColor
                )
                Spacer(Modifier.height(56.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    GetSingleAuthNumber(first) {
                        first = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                    }
                    GetSingleAuthNumber(second) {
                        second = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                        else if (second.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                    }
                    GetSingleAuthNumber(third) {
                        third = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                        else if (third.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                    }
                    GetSingleAuthNumber(fourth) {
                        fourth = it
                        if (fourth.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                        else {
                            val certNumber = buildString {
                                append(first)
                                append(second)
                                append(third)
                                append(it)
                            }
                            viewModel.onEvent(SignUpEvent.CertChanged(certNumber))
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
                OutlinedButton(
                    onClick = {
                        timer = 150
                        viewModel.onEvent(SignUpEvent.RequestCertNumber)
                    },
                    shape = RoundedCornerShape(23.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    border = BorderStroke(1.dp, MainColor)
                ) {
                    Text(
                        text = "인증번호 재발송",
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MainColor
                    )
                }
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}

@Composable
fun GetSingleAuthNumber(
    authSingleValue: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = authSingleValue,
        onValueChange = onValueChanged,
        modifier = Modifier
            .size(58.dp),
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 21.sp,
            color = MainColor,
            textAlign = TextAlign.Center
        ),
        shape = RoundedCornerShape(9.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MainColor,
            focusedBorderColor = MainColor,
            unfocusedBorderColor = Color.Transparent,
            backgroundColor = Color(0xFFFBF8F6)
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

private fun toTimer(time: Int): String {
    return buildString {
        append(time / 60)
        append(":")
        if (time % 60 < 10) append("0")
        append(time % 60)
    }
}