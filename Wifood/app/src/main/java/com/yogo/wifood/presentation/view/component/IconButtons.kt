package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yogo.wifood.R


@Composable
fun VectorIconWithNoInteraction(
    resource: Int = R.drawable.ic_camera_button,
    onClick: () -> Unit = {},
    width: Int = 0,
    height: Int = 0,
){
    val interactionSource = MutableInteractionSource()
    var modifier = Modifier
    if (width < 1 || height < 1) {
        modifier
            .width(width.dp)
            .height(height.dp)
    } else {
        modifier
            .wrapContentSize()
    }
    Icon(
        ImageVector.vectorResource(id = resource),
        contentDescription = "",
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ){
                onClick()
            },
        tint = Color.Unspecified
    )
}

@Composable
fun VectorIconWithInteraction(
    resource: Int = R.drawable.ic_camera_button,
    onClick:() -> Unit = {},
    width:Int = 0,
    height:Int = 0,
){
    var modifier = Modifier
    if (width < 1 || height < 1) {
        modifier
            .width(width.dp)
            .height(height.dp)
    } else {
        modifier
            .wrapContentSize()
    }
    Icon(
        ImageVector.vectorResource(id = resource),
        contentDescription = "",
        modifier = Modifier
            .clickable(
            ){
                onClick()
            },
        tint = Color.Unspecified
    )
}