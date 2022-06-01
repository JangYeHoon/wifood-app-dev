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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.ui.theme.fontRoboto
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray09Color
import com.example.wifood.view.ui.theme.InsideButtonTextColor

@Composable
fun TextInsideButton(
    text:String = "인증번호 받기",
    modifier:Modifier,
    width:Int = 85,
    height:Int = 30,
    onClick:() -> Unit = {}
){
    TextButton(
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Gray09Color
        ),
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
    )
    {
        Text(
            text = text,
            color = InsideButtonTextColor,
            fontSize = 10.sp,
            fontFamily = fontRoboto,
            fontWeight = FontWeight.Normal
        )
    }
}