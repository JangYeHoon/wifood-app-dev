package com.example.wifood.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.ThickDivider
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.groupComponet.ProfileBoxImageVector
import com.example.wifood.presentation.view.mypage.component.CommonTextButton

@Composable
fun MyPageComposeView(
    navController: NavController
) {
    // UI variables
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "설정",
                leftButtonClicked = {
                    navController.popBackStack()
                }
            )
        },
    ){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.EditProfile.route)
                    }
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 20.dp)
            ){
                Row(
                ){
                    ProfileBoxImageVector(
                        userNickname = "닉네임",
                        userId = "nickname@naver.com",
                        userProfileImage = R.drawable.ic_splash_icon
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_right_arrow),
                        contentDescription = "back button",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified
                    )
                }
            }
            ThickDivider(thickness = 4)
            Column(
            ){
                CommonTextButton(
                    text = "내 정보 수정",
                    withButton = true,
                    onClick = {
                        navController.navigate(Route.EditMyInfo.route)
                        // TODO
                    }
                )
                CommonTextButton(
                    text = "앱 정보",
                    withButton = true,
                    onClick = {
                        navController.navigate(Route.AppInfo.route)
                    }
                )
                CommonTextButton(
                    text = "로그아웃",
                    withButton = false,
                    onClick = {
                        // TODO("should add log out logic")

                    }
                )
            }
        }
    }
}