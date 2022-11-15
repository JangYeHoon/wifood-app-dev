package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yogo.wifood.R

@Composable
fun SnsIconButton(
    resourceId:Int,
    size:Int = 40,
    onClick:()->Unit
){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .width(size.dp)
            .height(size.dp)
    ){
        Icon(
            ImageVector.vectorResource(id = resourceId),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            tint = Color.Unspecified
        )
    }
}