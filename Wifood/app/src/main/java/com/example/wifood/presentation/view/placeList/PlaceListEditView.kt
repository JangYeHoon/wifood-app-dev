package com.example.wifood.presentation.view.placeList

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.component_box.TextAndInputField

@Composable
fun PlaceListEditView(
    preGroupName:String ="그룹명",
    preGourpExplain:String = "샤로수길 맛집 저장소"
){

    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text="맛집 그룹 수정",
                onBackButtonClicked={/*TODO*/}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ){
            Spacer(Modifier.height(30.dp))
            TextAndInputField(
                titleText = "그룹명",
                inputFieldPlaceHolder = "그룹명",
                inputFieldText = preGroupName
            )
            Spacer(Modifier.height(15.dp))
            TextAndInputField(
                titleText = "상세설명",
                inputFieldPlaceHolder = "상세설명",
                inputFieldText = preGourpExplain
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            MainButton(
                text = "맛집 수정하기",
                onClick = {/*TODO*/}
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}