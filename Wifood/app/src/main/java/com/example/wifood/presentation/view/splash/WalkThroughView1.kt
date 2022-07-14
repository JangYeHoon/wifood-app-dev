package com.example.wifood.presentation.view.splash

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.login.component.TransparentButton
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun WorkThroughView1(
    navController : NavController
) {
    Scaffold(
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= buildAnnotatedString {
                    append("맛집 기록 어플, ")
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ){
                        append("요고")
                    }
                    append("\n한 줄 설명")
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(56.dp))
            Image(
                painter = painterResource(R.drawable.ic_walk_through_screen1),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(200.dp)
                    .height(272.dp)
            )
            Spacer(Modifier.height(49.dp))
        }
    }
}