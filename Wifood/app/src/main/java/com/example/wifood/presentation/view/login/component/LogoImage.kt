package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.R

@Composable
fun LogoImage(){
    val logoResource = R.drawable.ic_login_logo
    val logoWidth = 86.dp
    val logoHeight = 28.dp

    Image(
        painter = painterResource(logoResource),
        contentDescription = "YOGO Login Logo",
        modifier = Modifier
            .height(logoHeight)
            .width(logoWidth),
    )
}

