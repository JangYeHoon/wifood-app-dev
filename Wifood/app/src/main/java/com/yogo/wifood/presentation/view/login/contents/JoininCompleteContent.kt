package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.fontMiddleSchool
import com.yogo.wifood.view.ui.theme.Gray03Color
import com.yogo.wifood.view.ui.theme.MainColor

//@Preview(showBackground = true)
@Composable
fun JoininCompleteContent(
){
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = buildAnnotatedString {
                    append("반갑습니다!\n")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = fontMiddleSchool,
                            fontWeight = FontWeight.Normal,
                            fontSize = 32.sp,
                            color = MainColor
                        ),
                    ) {
                        append("회원가입")
                    }
                    append("이 완료되었습니다.")
                },
                fontFamily = fontMiddleSchool,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(18.dp))
            Image(
                painter = painterResource(R.drawable.ic_joinin_complete),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .wrapContentSize()
            )
        }
    }
}