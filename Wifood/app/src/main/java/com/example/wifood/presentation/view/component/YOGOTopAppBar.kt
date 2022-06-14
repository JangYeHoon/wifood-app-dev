package com.example.wifood.presentation.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.ui.theme.mainFont

@Composable

fun YOGOTopAppBar(
    text:String,
    onBackButtonClicked:() -> Unit
){
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.Center
            ) {
                Text(
                    text = text,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            navigationBackButton(
                onClick = onBackButtonClicked
            )
        },
        backgroundColor = Color.White,
        actions = {
            Spacer(modifier = Modifier.width(70.dp))
        }
    )
}