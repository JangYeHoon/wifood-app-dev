package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.yogo.wifood.presentation.view.component.MainButtonInversed
import com.yogo.wifood.presentation.view.component.ProgressIndicator
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.MainColor
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue

//@Preview(showBackground = true)
@Composable
fun GetAuthenticationNumberContent(
    isLoading: Boolean = false,
    timeText: String = "2:30",
    checkAuthNumber: (String) -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val scrollState = rememberScrollState() // for horizontal mode screen
    val focusManager = LocalFocusManager.current

    var firstAuthNumber by remember { mutableStateOf("") }
    var secondAuthNumber by remember { mutableStateOf("") }
    var thirdAuthNumber by remember { mutableStateOf("") }
    var fourthAuthNumber by remember { mutableStateOf("") }


    if (isLoading) {
        ProgressIndicator()
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
                text = timeText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = if (timeText != "0:00") MainColor else Color.Red
            )
            Spacer(Modifier.height(56.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                GetSingleAuthNumber(firstAuthNumber) {
                    if (it.isDigitsOnly()) {
                        firstAuthNumber = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                    }
                }

                GetSingleAuthNumber(secondAuthNumber) {
                    if (it.isDigitsOnly()) {
                        secondAuthNumber = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                        else if (secondAuthNumber.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                    }
                }
                GetSingleAuthNumber(thirdAuthNumber) {
                    if (it.isDigitsOnly()) {
                        thirdAuthNumber = it
                        if (it.length == 1) focusManager.moveFocus(FocusDirection.Right)
                        else if (thirdAuthNumber.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                    }
                }
                GetSingleAuthNumber(fourthAuthNumber) {
                    if (it.isDigitsOnly()) {
                        fourthAuthNumber = it
                        if (fourthAuthNumber.isBlank()) focusManager.moveFocus(FocusDirection.Left)
                        else {
                            val certNumber = buildString {
                                append(firstAuthNumber)
                                append(secondAuthNumber)
                                append(thirdAuthNumber)
                                append(it)
                            }
                            checkAuthNumber(certNumber)
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Spacer(modifier = Modifier.height(200.dp))
        }
        MainButtonInversed(
            text = "인증번호 재발송",
            onClick = onButtonClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = buttonBottomValue.dp)
                .padding(sidePaddingValue.dp)
        )
    }
}

@Composable
fun GetSingleAuthNumber(
    authSingleValue: String = "",
    onValueChanged: (String) -> Unit = {}
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