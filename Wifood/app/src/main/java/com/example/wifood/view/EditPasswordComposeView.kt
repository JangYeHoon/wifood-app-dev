package com.example.wifood.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun EditPasswordComposeView() {
    val newPassword = remember {
        mutableStateOf("")
    }

    val checkPassword = remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "비밀번호 변경")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(40.dp)) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                backgroundColor = Color.White,
                actions = {
                    Spacer(modifier = Modifier.width(70.dp))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 31.dp)
        ) {
            Text(
                text = "새로운 비밀번호를 입력해주세요.",
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 17.dp)
            )
            Text(
                text = "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 17.dp)
            )
            TextField(
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                placeholder = { Text(text = "비밀번호") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier
                    .width(350.dp)
                    .padding(start = 17.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "비밀번호 확인", fontSize = 15.sp, modifier = Modifier.padding(start = 17.dp))
            TextField(
                value = checkPassword.value,
                onValueChange = { checkPassword.value = it },
                placeholder = { Text(text = "비밀번호 확인") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier
                    .width(350.dp)
                    .padding(start = 17.dp)
            )

            Spacer(modifier = Modifier.height(450.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(280.dp, 46.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Magenta
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("비밀번호 변경하기", color = Color.White)
            }
        }
    }
}