package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.groupComponent.SimpleInputView

@Composable
fun SetPlaceGroupView(
){
    Column {
        MyPageTopAppBar(
            titleText = "",
            leftButtonClicked = {

            },
            showUnderLine = false
        )
        SimpleInputView(
            explainText = "나만의 맛집\n그룹명을 지정해주세요",
            textFieldText = "",
            onTextFieldValueChanged = {},
            placeholderText = "맛집그룹명",
            buttonText = "다음",
            onButtonClick = {},
            buttonActivate = false
        )
    }
}