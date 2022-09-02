package com.example.wifood.presentation.view.placeList.newPlaceInfo

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wifood.presentation.view.groupComponet.SimpleInputView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetPlaceGroupDetailView(
){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        SimpleInputView(
            explainText = "그룹에 대한\n간단한 설명을 해주세요.",
            textFieldText = "",
            onTextFieldValueChanged = {},
            placeholderText = "맛집 그룹 설명",
            buttonText = "맛집 그룹 등록하기",
            onButtonClick = {},
            buttonActivate = false
        )
    }
}