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
fun LogoImage(
    width:Int = 86,
    height:Int = 28
){
    val logoResource = R.drawable.ic_login_logo
    Image(
        painter = painterResource(logoResource),
        contentDescription = "YOGO Login Logo",
        modifier = Modifier
            .height(height.dp)
            .width(width.dp),
    )
}

