package com.example.wifood.presentation.view.login.join

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.*
import androidx.compose.ui.unit.R
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue


@Composable
fun JoininCompleteView(
    navController : NavController,
    userNickName : String = "닉네임"
){
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text= buildAnnotatedString {
                    append("반갑습니다, ")
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ){
                        append(userNickName)
                    }
                    append("님\n 회원가입이 완료되었습니다.")
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(56.dp))
            Image(
                painter = painterResource(com.example.wifood.R.drawable.ic_joinin_complete_cat),
                contentDescription = "joinin complete cate image",
                modifier = Modifier
                    .width(253.dp)
                    .height(294.dp)
            )
            Spacer(Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "확인"
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}