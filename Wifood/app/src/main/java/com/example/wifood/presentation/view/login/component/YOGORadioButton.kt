package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun YOGORadioButton(
    text:String = "여성",
    width:Int = 50,
    height:Int = 32,
    onClick:() -> Unit = {},
    selected:Boolean = false
){
    TextButton(
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
        colors = if (selected) ButtonDefaults.outlinedButtonColors(
            contentColor = MainColor,
        ) else ButtonDefaults.buttonColors(backgroundColor = Gray09Color),
        border = BorderStroke(1.dp, if (selected) MainColor else Gray09Color),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    )
    {
        Text(
            text = text,
            color = if (selected) MainColor else Gray03Color,
            fontSize = 13.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium
        )
    }
}
