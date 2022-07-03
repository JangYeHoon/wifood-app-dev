package com.example.wifood.presentation.view.component

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit = {},
    activate: Boolean = true
) {
    TextButton(
        shape = RoundedCornerShape(23.dp),
        onClick = onClick,
        enabled = activate,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (activate) MainColor else EnableColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
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