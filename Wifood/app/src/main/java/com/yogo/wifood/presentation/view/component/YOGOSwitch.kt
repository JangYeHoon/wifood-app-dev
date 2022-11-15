package com.yogo.wifood.presentation.view.component

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun YOGOSwitch(
    checked:Boolean = false,
    onCheckedChange:((Boolean) -> Unit)? = null,
    modifier:Modifier = Modifier
){
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MainColor
        ),
        modifier = modifier
    )
}