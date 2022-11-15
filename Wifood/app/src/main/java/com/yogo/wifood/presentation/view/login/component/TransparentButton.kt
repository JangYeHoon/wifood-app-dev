package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray03Color

@Composable
fun TransparentButton(
    text:String,
    textColor:Color,
    textSize:Int = 12,
    onClick:()->Unit,
    textDecoration:TextDecoration? = null,
    customModifier:Modifier?=null,
){
    TextButton(
        shape = RoundedCornerShape(23.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Text(
            text = text,
            color = textColor,
            fontSize = textSize.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            textDecoration = textDecoration
        )
    }
}