package com.example.wifood.presentation.view.login.join

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData

@Composable
fun TestView(

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            TitleText(
                text = SignUpData.phoneNumber,
                color = Color.Black
            )
            TitleText(
                text = SignUpData.address,
                color = Color.Black
            )
            TitleText(
                text = SignUpData.birthday,
                color = Color.Black
            )
            TitleText(
                text = SignUpData.gender,
                color = Color.Black
            )
            TitleText(
                text = SignUpData.taste.toString(),
                color = Color.Black
            )
        }
    }
}