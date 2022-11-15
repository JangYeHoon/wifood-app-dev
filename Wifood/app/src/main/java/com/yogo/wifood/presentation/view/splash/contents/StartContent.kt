package com.yogo.wifood.presentation.view.splash.contents

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.login.component.LogoImage
import com.yogo.wifood.ui.theme.fontTmoney
import com.yogo.wifood.view.ui.theme.MainColor

//@Preview(showBackground = true)
@Composable
fun StartContent(
    scale: Animatable<Float, AnimationVector1D>? = null
) {
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
                .height((366.82).dp)
        )
    }
}
