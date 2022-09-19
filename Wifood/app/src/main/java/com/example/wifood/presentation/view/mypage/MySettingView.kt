package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.mypage.contents.MySettingContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MySettingView(
    navController: NavController
) {
    // UI variables
    MySettingContent(
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
            WifoodApp.pref.setString("Initial_Flag", "0")
            navController.navigate(Route.GetPhoneNumber.route)
        }
    )
}