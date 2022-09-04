package com.example.wifood.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wifood.view.ui.theme.dialogCenterDividerColor


@Composable
fun DialogCenterDivider(
    width:Int = 62,
    thickness:Int = 4
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .width(width.dp)
                .background(
                    color = dialogCenterDividerColor,
                    shape = RoundedCornerShape(2.dp)
                )
        ){
            Spacer(Modifier.height(thickness.dp))
        }
    }
}