package com.yogo.wifood.presentation.view.splash

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.splash.contents.StartContent
import com.yogo.wifood.util.Constants
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StartView(
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
        if (com.yogo.wifood.WifoodApp.pref.getString("Initial_Flag", "0") == "0") {
            navController.navigate(Route.Onboarding.route)
        } else {
            navController.navigate(Route.Main.route)
        }
    }

    // Content
    StartContent(
        scale = scale
    )
}