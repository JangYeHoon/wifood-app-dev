package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogo.wifood.view.ui.theme.Main
import com.yogo.wifood.view.ui.theme.Shapes
import com.yogo.wifood.view.ui.theme.White

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = Main,
                shape = Shapes.medium
            ).fillMaxWidth()
    ) {
        Text(
            text = text,
            color = White
        )
    }
}