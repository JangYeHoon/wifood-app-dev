package com.example.wifood.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchPlaceComposeView(
    navController: NavController
) {
    val searchKeyword = remember {
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
                        Text(text = "장소 검색")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(40.dp)
                    ) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchKeyword.value,
                    onValueChange = { searchKeyword.value = it },
                    modifier = Modifier.width(350.dp)
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Search, contentDescription = "")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listOf("순대국", "피자", "치킨")) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Row {
                                Text(text = it, fontSize = 16.sp)
                                Text(text = "음식점")
                            }
                            Text(text = "서울시 동작구 사당로")
                        }
                    }
                }
            }
        }
    }
}