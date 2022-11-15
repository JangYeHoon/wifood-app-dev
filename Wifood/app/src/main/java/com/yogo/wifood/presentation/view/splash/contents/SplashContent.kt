package com.yogo.wifood.presentation.view.splash.contents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
fun SplashContent(
    title: String = "맛집 기록 어플, ",
    edge: String = "요고",
    description: String = "\n내 취향의 맛집들을 찾고",
    imageWidth: Int = 232,
    imageHeight: Int = 314,
    image: Int = R.drawable.ic_splash_image_1
) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
        Spacer(Modifier.height(18.dp))
        Icon(
            ImageVector.vectorResource(image),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize(),
            tint = Color.Unspecified
        )
        Spacer(Modifier.weight(1f))
    }
}