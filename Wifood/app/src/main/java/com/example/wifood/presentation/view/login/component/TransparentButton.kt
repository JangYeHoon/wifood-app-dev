package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.example.wifood.ui.theme.mainFont

@Composable
fun TransparentButton(
    text:String,
    textColor:Color,
    textSize:Int,
    width:Int,
    height:Int,
    onClick:()->Unit,
    textDecoration:TextDecoration? = null
){
    TextButton(
        shape = RoundedCornerShape(23.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
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