package com.example.wifood.presentation.view.start

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.component.TransparentButton
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.sidePaddingValue

@Composable
fun WorkThroughView3(
    navController : NavController
) {
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= buildAnnotatedString {
                    append("내 취향의 맛집을 ")
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ){
                        append("추천")
                    }
                    append("받고\n한 줄 설명")
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(41.dp))
            Image(
                painter = painterResource(R.drawable.ic_walk_through_screen3),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(197.dp)
                    .height(210.dp)
            )
            Spacer(Modifier.height(99.dp))
            MainButton(
                text="시작하기",
                onClick = {/*TODO*/}
            )
        }
    }
}