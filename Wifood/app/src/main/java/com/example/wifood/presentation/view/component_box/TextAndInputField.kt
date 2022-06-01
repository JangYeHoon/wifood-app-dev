package com.example.wifood.presentation.view.component_box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.login.component.TitleText

@Preview(showBackground = true)
@Composable
fun TextAndInputField(
    titleText:String = "그룹명",
    inputFieldPlaceHolder:String="그룹명",
    inputFieldText:String = "",
){
    Column {
        TitleText(text=titleText)
        Spacer(Modifier.height(5.dp))
        InputTextField(
            text=inputFieldText,
            placeholder = inputFieldPlaceHolder
        )
    }
}
