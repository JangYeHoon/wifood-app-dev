package com.example.wifood.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton

@Composable
fun EditMyInfoComposeView(
    navController: NavController
) {
    // UI variables
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "내 정보 수정",
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ){
            CommonTextButton(
                text = "비밀번호 변경",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "휴대폰 번호 변경",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "주소 변경",
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
                text = "회원 탈퇴",
                withButton = false,
                onClick = {
                    //TODO
                }
            )
        }
    }
}