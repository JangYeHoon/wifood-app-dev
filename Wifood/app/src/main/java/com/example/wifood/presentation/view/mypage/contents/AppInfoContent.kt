package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.component.versionInfoField

@Composable
fun AppInfoView(

){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText =  "앱정보",
            leftButtonOn = true,
            leftButtonClicked = {

            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ){
            versionInfoField(
                text = "버젼 정보",
                version = "1.0v",
                onClick = {}
            )
            CommonTextButton(
                text = "피드백",
                withButton = false,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "개발자 정보",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "서비스 이용약관",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
        }
    }
}