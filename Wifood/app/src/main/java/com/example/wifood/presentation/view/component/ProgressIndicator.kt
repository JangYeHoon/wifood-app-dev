package com.example.wifood.presentation.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.wifood.R
import com.example.wifood.view.ui.theme.Gray03Color

@Composable
fun ProgressIndicator(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray03Color.copy(0.5f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.progress_indicator_1),
            contentDescription = "Loading...",
            alpha = 1f
        )
    }

}