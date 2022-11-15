package com.yogo.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.mypage.component.CommonTextButton

//@Preview(showBackground = true)
@Composable
fun ModifyMyInfoContent(
    onBackButtonClicked: () -> Unit = {},
    onModifyPhoneNumberClicked: () -> Unit = {},
    onModifyUserLocationClicked: () -> Unit = {},
    onModifyUserFavorClicked: () -> Unit = {},
    onWithdrawClicked: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "내 정보 수정",
            leftButtonOn = true,
            leftButtonClicked = {
                onBackButtonClicked()
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            CommonTextButton(
                text = "휴대폰 번호 변경",
                withButton = true,
                onClick = {
                    onModifyPhoneNumberClicked()
                }
            )
            CommonTextButton(
                text = "내 동네 변경",
                withButton = true,
                onClick = {
                    onModifyUserLocationClicked()
                }
            )
            CommonTextButton(
                text = "내 입맛 수정",
                withButton = true,
                onClick = {
                    onModifyUserFavorClicked()
                }
            )
            CommonTextButton(
                text = "회원탈퇴",
                withButton = false,
                onClick = {
                    onWithdrawClicked()
                }
            )
        }
    }
}
