package com.example.wifood.presentation.view.login.new_compose_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.naver.maps.map.overlay.Align

@Composable
fun GetPhoneAuthenticationNumberView(

){
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen
    var phoneNumberValidation = false
    var phoneText by remember { mutableStateOf(TextFieldValue(text = "")) }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ){
            Spacer(Modifier.height(106.dp))
            Text(
                text = "인증번호 4자리를\n입력해주세요.",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "2:30",
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MainColor
            )
            Spacer(Modifier.height(56.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                GetSingleAuthNumber("7")
                GetSingleAuthNumber("5")
                GetSingleAuthNumber("6")
                GetSingleAuthNumber()
            }
            Spacer(Modifier.weight(1f))
            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                border = BorderStroke(1.dp, MainColor)
            ){
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

@Composable
fun GetSingleAuthNumber(
    authSingleValue : String = ""
){
    OutlinedTextField(
        value = authSingleValue,
        onValueChange = {
        },
        modifier = Modifier
            .size(58.dp),
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 21.sp,
            color = MainColor,
            textAlign = TextAlign.Center
        ),
        placeholder = {
            Text(
                text = "-",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 21.sp,
                color = EnableColor,
                textAlign = TextAlign.Center
            )
        },
        shape = RoundedCornerShape(9.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MainColor,
            focusedBorderColor = MainColor,
            unfocusedBorderColor = Color.Transparent,
            backgroundColor = Color(0xFFFBF8F6)
        )
    )
}