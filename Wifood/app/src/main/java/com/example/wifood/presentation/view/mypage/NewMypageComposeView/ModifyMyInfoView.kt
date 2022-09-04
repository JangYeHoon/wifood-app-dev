package com.example.wifood.presentation.view.mypage.NewMypageComposeView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.component.versionInfoField

@Composable
fun ModifyMyInfoView(

){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "내 정보 수정",
            leftButtonOn = true,
            leftButtonClicked = {

            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ){
            CommonTextButton(
                text = "휴대폰 번호 변경",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "내 동네 변경",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "내 입맛 수정",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "회원탈퇴",
                withButton = false,
            )
        }
    }
}
