package com.yogo.wifood.presentation.view.groupComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogo.wifood.presentation.view.login.component.InputTextField
import com.yogo.wifood.presentation.view.login.component.TitleText

@Composable
fun TextAndInputField(
    titleText: String = "그룹명",
    inputFieldPlaceHolder: String = "그룹명",
    inputFieldText: String = "",
    onValueChange: (String) -> Unit,
    onValueReset: () -> Unit = {}
) {
    Column {
        TitleText(text = titleText)
        Spacer(Modifier.height(5.dp))
        InputTextField(
            text = inputFieldText,
            placeholder = inputFieldPlaceHolder,
            onValueChange = onValueChange,
            onValueReset = onValueReset
        )
    }
}
