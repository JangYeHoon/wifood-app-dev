package com.yogo.wifood.presentation.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.mypage.contents.MySettingContent

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
            com.yogo.wifood.WifoodApp.pref.setString("Initial_Flag", "0")
            navController.navigate(Route.GetPhoneNumber.route) {
                popUpTo(0)
            }
        }
    )
}