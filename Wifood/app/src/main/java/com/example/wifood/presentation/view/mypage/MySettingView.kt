package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.mypage.contents.MySettingContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyPageComposeView(
    navController: NavController
) {
    // UI variables
    MySettingContent(
        nicknameText = "요고247",
        onNicknameClicked = {
            navController.navigate(Route.EditProfile.route)
        },
        onModifyMyInfoClicked = {
            navController.navigate(Route.EditMyInfo.route)
        },
        onAppInfoClicked = {
            navController.navigate(Route.AppInfo.route)
        },
        onLogoutClicked = {
            // TODO(로그아웃 눌렀을때)
        }
    )
}