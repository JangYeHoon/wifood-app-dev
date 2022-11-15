package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyPageTAB(

) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.Center
            ) {
                Text(text = "마이페이지")
            }
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(40.dp)) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            Spacer(modifier = Modifier.width(70.dp))
        }
    )
}