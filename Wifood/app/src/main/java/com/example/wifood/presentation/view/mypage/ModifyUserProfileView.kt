package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.wifood.presentation.view.mypage.contents.ModifyMyProfileContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditProfileComposeView(
    navController: NavController
) {
    val editNickname = remember {
        mutableStateOf("요고 247")
    }
    ModifyMyProfileContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onCameraButtonClicked = {
            //TODO(카메라 버튼 눌렀을 때)
        },
        nicknameText = "요고247" , // TODO(사용자 아이디로 입력받도록)
        onNicknameTextChanged = {
            //TODO(닉네임 바뀔때마다)
        },
        onCompleteButtonClicked = {
            //TODO(완료버튼 눌렀을때)
        }
    )
}