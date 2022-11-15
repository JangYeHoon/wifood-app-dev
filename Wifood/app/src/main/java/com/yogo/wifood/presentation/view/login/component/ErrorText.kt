package com.yogo.wifood.presentation.view.login.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.ErrorColor

@Composable
fun ErrorText(
    text:String ="에러 텍스트입니다.",
    visibility:Boolean = false
){
    Text(
        text = text,
        color = if (visibility) ErrorColor else Color.Transparent,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
    )
}