package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.presentation.view.mypage.contents.ModifyUserProfileContent

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserProfileView(
    navController: NavController
) {
    ModifyUserProfileContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onCameraButtonClicked = {
            //TODO(카메라 버튼 눌렀을 때)
        },
        onNicknameTextChanged = {
            //TODO(닉네임 바뀔때마다)
        },
        onCompleteButtonClicked = {
            //TODO(완료버튼 눌렀을때)
        }
    )
}