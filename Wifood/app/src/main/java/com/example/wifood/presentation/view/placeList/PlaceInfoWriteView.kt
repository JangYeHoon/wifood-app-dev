package com.example.wifood.presentation.view.placeList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.placeList.component.ListSelectionButton
import com.example.wifood.view.ui.theme.DividerColor

@Preview(showBackground = true)
@Composable
fun PlaceInfoWriteView(){
    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text="맛집 등록",
                onBackButtonClicked={/*TODO*/}
            )
        }
    ) {
        Column(
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ){
                ListSelectionButton(
                    buttonText = "그룹 선택",
                    onClick = {/*TODO*/}
                )
                Divider(
                    color = DividerColor,
                    modifier = Modifier.height(1.dp)
                )
                ListSelectionButton(
                    buttonText = "맛집 선택",
                    onClick = {/*TODO*/}
                )
                Divider(
                    color = DividerColor,
                    modifier = Modifier.height(1.dp)
                )
                ListSelectionButton(
                    buttonText = "메뉴",
                    onClick = {/*TODO*/}
                )
                Divider(
                    color = DividerColor,
                    modifier = Modifier.height(1.dp)
                )
                // 방문여부 넣기
            }
            Column(){

            }
            Column(
                modifier = Modifier.padding(horizontal=24.dp)

            ){

            }

        }
    }
}