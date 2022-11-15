package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@Composable
fun YOGORadioButton(
    text:String = "여성",
    width:Int = 50,
    height:Int = 32,
    onClick:() -> Unit = {},
    selected:Boolean = false
){
    val interactionSource = remember { MutableInteractionSource() }

    TextButton(
        shape = CircleShape,
        onClick = onClick,
        colors = if (selected) ButtonDefaults.outlinedButtonColors(
            contentColor = MainColor,
        ) else ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(1.dp, if (selected) MainColor else EnableColor),
        modifier = Modifier
            .size(84.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {

            }
    )
    {
        Text(
            text = text,
            color = if (selected) MainColor else EnableColor,
            fontSize = 18.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold
        )
    }
}
