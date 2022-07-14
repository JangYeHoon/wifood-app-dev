package com.example.wifood.presentation.view.splash.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.splash.Page
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun PageUI(page: Page) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = buildAnnotatedString {
                    append(page.title)
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ) {
                        append(page.edge)
                    }
                    append(page.description)
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(56.dp))
            Image(
                painter = painterResource(page.image),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(200.dp)
                    .height(272.dp)
            )
            Spacer(Modifier.height(49.dp))
        }
    }
}