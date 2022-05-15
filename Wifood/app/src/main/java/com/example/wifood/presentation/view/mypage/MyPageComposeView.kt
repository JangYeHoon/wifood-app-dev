package com.example.wifood.presentation.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.R

@Preview
@Composable
fun MyPageComposeView() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "마이페이지")
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
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .padding(24.dp, 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painterResource(id = R.drawable.profile),
                        contentDescription = "",
                        Modifier.size(60.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Column {
                        Text(text = "닉네임")
                        Text(text = "UserName@wifood.com")
                    }
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(20.dp, 25.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = ""
                    )
                }
            }

            Divider(color = Color.Gray, modifier = Modifier.height(1.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listOf("내 정보 수정", "앱정보", "로그아웃")) {
                    // TODO: settings, mypage view에서 동일하게 쓰임, 공통부분 함수로 빼기
                    Box(
                        modifier = Modifier.fillMaxWidth().height(45.dp).padding(24.dp, 0.dp)
                    ) {
                        Text(text = it, modifier = Modifier.align(Alignment.CenterStart))
                        if (it != "로그아웃") {
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
}