package com.example.wifood.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.R
import com.gowtham.ratingbar.RatingBar

@Composable
fun EditPlaceComposeView(
    navController: NavController
) {
    val editMenu = remember {
        mutableStateOf("")
    }

    val visited = remember {
        mutableStateOf(false)
    }

    val rating = remember {
        mutableStateOf(1.0f)
    }

    val review = remember {
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
                        Text(text = "맛집 등록")
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listOf("그룹 선택", "맛집 선택")) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "*")
                        Text(text = it)

                        Spacer(modifier = Modifier.width(240.dp))

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.ArrowForward, "")
                        }
                    }
                    Divider(color = Color.LightGray, modifier = Modifier.width(350.dp))
                }
            }

            // TODO: 중복 부분 함수로 빼기
            TextField(
                value = editMenu.value,
                onValueChange = { editMenu.value = it },
                placeholder = { Text(text = "메뉴") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(360.dp)
            )
            Divider(color = Color.LightGray, modifier = Modifier.width(350.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "방문여부")
                Switch(
                    checked = visited.value,
                    onCheckedChange = { visited.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Yellow,
                        checkedTrackColor = Color.Yellow
                    )
                )
            }

            LazyColumn {
                items(listOf("맛", "위생", "친절")) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it)
                        RatingBar(
                            value = rating.value,
                            onValueChange = { rating.value = it },
                            onRatingChanged = { rating.value = it }
                        )
                    }
                }
            }

            Row {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(60.dp)
                        .border(1.dp, Color.Gray)
                ) {
                    Icon(Icons.Filled.Home, "")
                }
                Spacer(modifier = Modifier.width(6.dp))

                LazyRow {
                    items(listOf(R.drawable.place_image, R.drawable.place_image)) {
                        Image(
                            painterResource(id = it),
                            contentDescription = "",
                            Modifier.size(60.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }

            Box {
                TextField(
                    value = review.value,
                    onValueChange = { review.value = it },
                    placeholder = { Text(text = "맛집 리뷰") },
                    modifier = Modifier
                        .border(1.dp, Color.Gray)
                        .size(312.dp, 108.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )
                Text("0/500", modifier = Modifier.align(Alignment.BottomEnd), color = Color.Gray)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "메뉴평가")

                Spacer(modifier = Modifier.width(240.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Add, "")
                }
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listOf("메뉴명", "가격", "메모")) {
                    // TODO: 중복 부분 함수로 빼기
                    TextField(
                        value = editMenu.value,
                        onValueChange = { editMenu.value = it },
                        placeholder = { Text(text = it) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.width(360.dp)
                    )
                    Divider(color = Color.LightGray, modifier = Modifier.width(350.dp))
                }
            }

            // TODO: 메뉴 평가 추가하면 LazyColumn에 하나씩 생성

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(280.dp, 46.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Magenta
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("맛집 등록하기", color = Color.White)
            }
        }
    }
}