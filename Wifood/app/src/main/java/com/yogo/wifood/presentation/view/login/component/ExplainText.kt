package com.yogo.wifood.presentation.view.login.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.ExplainTextColor

@Composable
fun ExplainText(
    text:String = "",
    color:Color = ExplainTextColor
){
    Text(
        text=text,
        color = color,
        fontSize = 12.sp,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal
    )
}