package com.example.wifood.presentation.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R

fun JoinView(navController: NavController){

}

val fontPretendard = FontFamily(
    Font(R.font.pbold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pmedium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pregular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.plight, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pthin, FontWeight.Thin, FontStyle.Normal),
)

@Preview(showBackground =  true)
@Composable
fun JoininContent(){
    var id by rememberSaveable {mutableStateOf("")}
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "회원가입")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.width(40.dp).height(40.dp))
                    {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_arrow),
                            contentDescription = "back button",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                    }
                },
                backgroundColor = Color.White,
                actions = {
                    Spacer(modifier = Modifier.width(70.dp))
                }
            )
        }

    ){
        Column(Modifier.padding(24.dp,16.dp)){
            Spacer(Modifier.height(15.dp))
            Text(
                text="아이디",
                color = Color(0xFF424242),
                fontSize = 15.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text="영문 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요",
                color = Color(0xFF565656),
                fontSize = 12.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = id,
                onValueChange = { id = it },
                placeholder = {
                    Text(
                        text = "아이디",
                        color = Color(0xFFCFCFCF)
                    )
                },
                singleLine = true,
                modifier = Modifier.width(312.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0xFF565656),
                    backgroundColor = Color.White,
                    cursorColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),
            )

            Spacer(Modifier.height(5.dp))
            Text(
                text="아이디",
                color = Color(0xFF424242),
                fontSize = 15.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text="영문 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요",
                color = Color(0xFF565656),
                fontSize = 12.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = id,
                onValueChange = { id = it },
                placeholder = {
                    Text(
                        text = "아이디",
                        color = Color(0xFFCFCFCF)
                    )
                },
                singleLine = true,
                modifier = Modifier.width(312.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0xFF565656),
                    backgroundColor = Color.White,
                    cursorColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),
            )
        }
    }

}