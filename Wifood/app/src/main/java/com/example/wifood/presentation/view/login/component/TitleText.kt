package com.example.wifood.presentation.view.login.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.TitleTextColor

@Composable
fun TitleText(
    text:String
){
    Text(
        text=text,
        color = TitleTextColor,
        fontSize = 15.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Bold
    )
}