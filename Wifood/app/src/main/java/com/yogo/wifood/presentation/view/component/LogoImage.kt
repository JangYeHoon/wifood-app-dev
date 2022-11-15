package com.yogo.wifood.presentation.view.login.component

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yogo.wifood.R
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.util.Constants
import kotlinx.coroutines.delay

@Composable
fun LogoImage(
    width: Int = 86,
    height: Int = 28,
    scale: Animatable<Float, AnimationVector1D>? = null
) {
    val logoResource = R.drawable.ic_login_logo


    Image(
        painter = painterResource(logoResource),
        contentDescription = "YOGO Login Logo",
        modifier = Modifier
            .height(height.dp)
            .width(width.dp)
            .scale(scale?.value ?: 1f),
    )
}

