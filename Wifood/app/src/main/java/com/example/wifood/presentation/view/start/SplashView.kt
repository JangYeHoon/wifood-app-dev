package com.example.wifood.presentation.view.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.login.component.LogoImage
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.*

@Composable
fun SplashView(
    navController : NavController
){
    Scaffold(
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "요리탐정 고양이",
                color = MainColor,
                fontSize = 13.sp,
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            LogoImage(
                width = 92,
                height = 31
            )
            Spacer(Modifier.height(45.dp))
            Image(
                painter = painterResource(R.drawable.ic_splash_icon),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(216.dp)
                    .height(315.dp)
            )
            Spacer(Modifier.height(0.dp))
        }
    }
}