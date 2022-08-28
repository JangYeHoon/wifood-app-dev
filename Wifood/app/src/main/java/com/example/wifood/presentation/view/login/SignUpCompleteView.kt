package com.example.wifood.presentation.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.wifood.ui.theme.fontMiddleSchool
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import kotlinx.coroutines.delay

@Composable
fun SignUpCompleteView(
    navController: NavController,
){
    val scrollState = rememberScrollState()
    LaunchedEffect(true) {
        delay(1500L)
        navController.navigate(Route.Main.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ){
            Text(
                text = "반갑습니다!회원가입이 완료되었습니다",
                fontFamily = fontMiddleSchool,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(48.5.dp))
            Image(
                painter = painterResource(R.drawable.ic_signup_complete),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(254.dp)
                    .height(294.dp)
            )
        }
    }
}

