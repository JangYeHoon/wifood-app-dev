package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.contents.ModifyMyInfoContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditMyInfoComposeView(
    navController: NavController
) {

    ModifyMyInfoContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onModifyPhoneNumberClicked = {
            // TODO(휴대폰 번호 변경 클릭 시)
        },
        onModifyUserLocationClicked = {
            // TODO(내 동네 변경 클릭시)
        },
        onModifyUserFavorClicked = {
            // TODO(내 입맛 수정 눌렀을때)
        },
        onWithdrawClicked = {
            // TODO(회원탈퇴 눌렀을 때)
        },
    )
}