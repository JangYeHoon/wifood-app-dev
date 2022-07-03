package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue

@Composable
fun FindPwdView(){
}

@Preview(showBackground = true)
@Composable
fun test(){
    var AuthPass = false
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text = "비밀번호 변경",
                onBackButtonClicked = {
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp)
                .padding(top = 31.dp)
        )
        {
            // Set phone check
            TitleText("새로운 비밀번호를 입력해주세요")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = password,
                placeholder = "비밀번호",
                onValueChange = {
                    password = it
                },
                isPassword = true
            )
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = passwordCheck,
                placeholder = "비밀번호 확인",
                onValueChange = {
                    passwordCheck = it
                },
                isPassword = true
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "비밀번호 변경하기",
                onClick = {
                },
                activate = AuthPass
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}
