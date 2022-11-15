package com.yogo.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.login.util.phoneFilter
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

//@Preview(showBackground = true)
@Composable
fun ModifyUserPhoneNumberContent(
    phoneNumberText: String = "",
    onPhoneNumberValueChanged: (String) -> Unit = {},
    onChangeButtonClicked: () -> Unit = {},
    done: Boolean
){
    val scrollState = rememberScrollState()

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
                text = "새로운 휴대폰 번호를\n입력해주세요.",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(24.dp))
            TextField(
                value = phoneNumberText,
                onValueChange = onPhoneNumberValueChanged,
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
                text = "변경하기",
                onClick = onChangeButtonClicked,
                activate = done
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}