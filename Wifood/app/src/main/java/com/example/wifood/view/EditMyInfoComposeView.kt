package com.example.wifood.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Preview
@Composable
fun EditMyInfoComposeView() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "내 정보 수정")
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listOf("비밀번호 변경", "휴대폰 번호 변경", "주소 변경", "내 입맛 수정", "회원탈퇴")) {
                // TODO: settings, mypage view에서 동일하게 쓰임, 공통부분 함수로 빼기
                Box(
                    modifier = Modifier.fillMaxWidth().height(45.dp).padding(24.dp, 0.dp)
                ) {
                    Text(text = it, modifier = Modifier.align(Alignment.CenterStart))
                    if (it != "회원탈퇴") {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.align(Alignment.CenterEnd).size(20.dp, 25.dp)
                        ) {
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}