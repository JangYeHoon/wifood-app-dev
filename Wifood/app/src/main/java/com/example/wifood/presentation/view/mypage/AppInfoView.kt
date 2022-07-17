package com.example.wifood.presentation.view.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.component.CommonTextButtonSB
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun AppInfoView(
    navController: NavController
){
    // UI variables
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "앱 정보",
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            CommonTextButtonSB(
                text = buildAnnotatedString {
                    append("버젼 정보")
                    Spacer(Modifier.weight(1f))
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ){
                        append("v1.0")
                    }
                },
                withButton = false,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "피드백",
                withButton = false,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "개발자 정보",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "서비스 이용약관",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
        }
    }
}