package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.Gray09Color
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun SelectedToggle(
    text:String = "남성",
    width:Int = 50,
    height:Int = 32,
    onClick:() -> Unit = {}
){
    TextButton(
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MainColor
        ),
        border = BorderStroke(1.dp,MainColor),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
    {
        Text(
            text = text,
            color = MainColor,
            fontSize = 13.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun UnSelectedToggle(
    text:String = "여성",
    width:Int = 50,
    height:Int = 32,
    onClick:() -> Unit = {}
){
    TextButton(
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Gray09Color,
        ),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
    {
        Text(
            text = text,
            color = Gray03Color,
            fontSize = 13.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium
        )
    }
}