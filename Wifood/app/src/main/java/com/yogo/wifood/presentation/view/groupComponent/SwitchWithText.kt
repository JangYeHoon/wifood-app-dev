package com.yogo.wifood.presentation.view.groupComponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogo.wifood.presentation.view.component.YOGOSwitch
import com.yogo.wifood.presentation.view.login.component.TitleText

@Composable
fun SwitchWithText(
    text: String = "방문 여부",
    spaceBetweenSwitch: Int = 14,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    switchModifier: Modifier = Modifier

) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleText(text = text)
        Spacer(Modifier.width(spaceBetweenSwitch.dp))
        YOGOSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = switchModifier
        )
    }

}
