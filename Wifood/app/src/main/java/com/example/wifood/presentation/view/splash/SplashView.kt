package com.example.wifood.presentation.view.splash

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.login.component.LogoImage
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.util.Constants
import com.example.wifood.view.ui.theme.MainColor
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashView(
    navController: NavController
) {
    val scale = remember {
        Animatable(0f)
    }
    val over = OvershootInterpolator(2f)

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 50,
                easing = Easing {
                    over.getInterpolation(it)
                }
            )
        )
        delay(Constants.SPLASH_SCREEN_DELAY)
        navController.popBackStack()
        // 앱을 맨 처음 실행했을 단 한 번의 경우에만 Onboarding 실행
        if (WifoodApp.pref.getString("Initial_Flag", "0") == "0") {
            navController.navigate(Route.Onboarding.route)
        } else {
            navController.navigate(Route.GetPhoneNumber.route)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "요리탐정 고양이",
                color = MainColor,
                fontSize = 13.sp,
                fontFamily = fontTmoney,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(10.dp))
            LogoImage(
                width = 92,
                height = 31,
                scale = scale
            )
            Spacer(Modifier.height(26.dp))
            Image(
                painter = painterResource(R.drawable.ic_splash_icon),
                contentDescription = "Splash View Image",
                modifier = Modifier
                    .width(240.dp)
                    .height(366.dp)
            )
        }
    }
}