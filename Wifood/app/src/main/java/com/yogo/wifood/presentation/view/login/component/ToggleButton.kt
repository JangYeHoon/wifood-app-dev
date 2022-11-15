package com.yogo.wifood.presentation.view.login.component

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogo.wifood.view.ui.theme.MainColor

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ToggleButton(
    isChecked: Boolean = false,
    onClick: () -> Unit = {},
    size:Int = 18
) {
    RadioButton(
        selected = isChecked,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            selectedColor = MainColor
        ),
        modifier = Modifier.width(size.dp).height(size.dp)
    )
}