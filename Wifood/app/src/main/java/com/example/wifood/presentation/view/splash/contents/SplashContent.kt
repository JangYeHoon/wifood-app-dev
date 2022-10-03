package com.example.wifood.presentation.view.splash.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.wifood.R
import com.example.wifood.ui.theme.fontMiddleSchool
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor


//@Preview(showBackground = true)
@Composable
fun SplashContent(
    title: String = "맛집 기록 어플, ",
    edge: String = "요고",
    description: String = "한 줄 설명",
    imageWidth: Int = 232,
    imageHeight: Int = 314,
    image: Int = R.drawable.ic_walk_through_screen1
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = buildAnnotatedString {
                    append(title)
                    withStyle(
                        style = SpanStyle(
                            fontFamily = fontMiddleSchool,
                            fontWeight = FontWeight.Normal,
                            fontSize = 32.sp,
                            color = MainColor
                        ),
                    ) {
                        append(edge)
                    }
                    append(description)
                },
                fontFamily = fontMiddleSchool,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(50.dp))
            Image(
                painter = painterResource(image),
                contentDescription = "Splash View Image",
                modifier = androidx.compose.ui.Modifier
                    .width(imageWidth.dp)
                    .height(imageHeight.dp)
            )
            Spacer(Modifier.weight(1f))
        }
    }
}