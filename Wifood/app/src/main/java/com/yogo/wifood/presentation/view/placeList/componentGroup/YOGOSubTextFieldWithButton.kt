package com.yogo.wifood.presentation.view.placeList.componentGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yogo.wifood.presentation.view.component.YOGOTextPM15
import com.yogo.wifood.presentation.view.placeList.component.YOGOBaseTextField

@Composable
fun YOGOSubTextFieldWithButton(
    titleText: String,
    inputText: String = "",
    placeholder: String = "맛집 그룹을 입력해주세요",
    onValueChange: (String) -> Unit = {},
    onTextFieldClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YOGOTextPM15(
            text = titleText
        )
        YOGOBaseTextField(
            text = inputText,
            onValueChange = onValueChange,
            placeholderText = placeholder,
            selectable = true,
            selectFunction = onTextFieldClick
        )
    }
}