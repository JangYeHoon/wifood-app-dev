package com.example.wifood.presentation.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wifood.view.ui.theme.ThickDividerColor

@Composable
fun ThickDivider(
    thickness:Int = 4
){
    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        color = ThickDividerColor,
        thickness = thickness.dp
    )
}