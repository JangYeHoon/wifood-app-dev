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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.login.component.TransparentButton
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun WorkThroughView2(
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
                    append("나만의 맛집을 ")
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ){
                        append("기록")
                    }
                    append("하고\n한 줄 설명")
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(56.dp))
            Image(
                painter = painterResource(R.drawable.ic_walk_through_screen2),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(200.dp)
                    .height(272.dp)
            )
            Spacer(Modifier.height(49.dp))
            TransparentButton(
                text = "SKIP",
                textColor = MainColor,
                textSize = 16,
                onClick = {},
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
