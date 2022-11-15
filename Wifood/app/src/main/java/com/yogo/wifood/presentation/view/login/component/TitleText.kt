package com.yogo.wifood.presentation.view.login.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.TitleTextColor

@Composable
fun TitleText(
    text:String="",
    color:Color = TitleTextColor
){
    Text(
        text=text,
        color = color,
        fontSize = 15.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Bold,
    )
}