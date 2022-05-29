package com.example.wifood.presentation.view.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.ui.theme.mainFont

@Composable
fun MainButton(
    text:String,
    width:Int = 280,
    height:Int = 50,
    onClick:() -> Unit
){
    TextButton(
        shape = RoundedCornerShape(23.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFEA7434)
        ),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
    {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold
        )
    }
}