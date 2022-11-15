package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun FindIdPwdButton(
    text: String = "로그인",
    onClick: () -> Unit = {},
    inverse: Boolean = false
){
    TextButton(
        shape = RoundedCornerShape(23.dp),
        onClick = onClick,
        border = BorderStroke(if (inverse) 1.dp else 0.dp, MainColor),
        colors =
        if (inverse )
            ButtonDefaults.outlinedButtonColors(
                contentColor = MainColor
            )
        else ButtonDefaults.buttonColors(
            backgroundColor =  MainColor
        ),
        modifier = Modifier
            .width(132.dp)
            .height(43.dp)
    )
    {
        Text(
            text = text,
            color = if (inverse) MainColor else Color.White,
            fontSize = 16.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal
        )
    }
}
