package com.example.wifood.presentation.view

import android.annotation.SuppressLint
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsComposeView() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "앱정보")
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
            items(listOf("버전 정보", "피드백", "개발자 정보", "서비스 이용약관")) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(45.dp).padding(24.dp, 0.dp)
                ) {
                    Text(text = it, modifier = Modifier.align(Alignment.CenterStart))
                    if (it == "버전 정보")
                        Text(text = "1.0v", modifier = Modifier.align(Alignment.CenterEnd))
                    else {
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