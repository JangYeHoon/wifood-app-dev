package com.example.wifood.presentation.view.component_box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.presentation.view.component.YOGOSwitch
import com.example.wifood.presentation.view.login.component.TitleText

@Composable
fun SwitchWithText(
    text:String = "방문 여부",
    spaceBetweenSwitch:Int = 14,
    checked:Boolean = false,
    onCheckedChange:((Boolean) -> Unit)? = null,
    switchModifier:Modifier = Modifier

){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleText(text = text)
        Spacer(Modifier.width(spaceBetweenSwitch.dp))
        YOGOSwitch(
            checked = false,
            onCheckedChange = onCheckedChange,
            modifier = switchModifier
        )
    }

}
