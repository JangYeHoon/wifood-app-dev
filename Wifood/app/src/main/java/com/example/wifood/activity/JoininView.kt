package com.example.wifood.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wifood.activity.ui.theme.WifoodTheme

class JoininView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutContent()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ConstraintLayoutContent(){
        var idText by remember { mutableStateOf("") }
        var pwdText by remember { mutableStateOf("") }
        var pwdCheckText by remember { mutableStateOf("") }
        var nicknameText by remember { mutableStateOf("") }
        var phoneText by remember { mutableStateOf("") }
        var phoneCheckText by remember { mutableStateOf("") }
        var addressCoarseText by remember { mutableStateOf("") }
        var addressFindText by remember { mutableStateOf("") }
        var birthText by remember { mutableStateOf("") }

        Column{
            TextField(
                value = idText,
                onValueChange = { idText = it },
                placeholder = {
                    Text(
                        text = "아이디",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )

            TextField(
                value = pwdText,
                onValueChange = { pwdText = it },
                placeholder = {
                    Text(
                        text = "비밀번호",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = pwdCheckText,
                onValueChange = { pwdCheckText = it },
                placeholder = {
                    Text(
                        text = "비밀번호 확인",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = nicknameText,
                onValueChange = { nicknameText = it },
                placeholder = {
                    Text(
                        text = "닉네임",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = phoneText,
                onValueChange = { phoneText = it },
                placeholder = {
                    Text(
                        text = "핸드폰 번호",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = phoneCheckText,
                onValueChange = { phoneCheckText = it },
                placeholder = {
                    Text(
                        text = "인증번호",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = addressCoarseText,
                onValueChange = { addressCoarseText = it },
                placeholder = {
                    Text(
                        text = "주소",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = addressFindText,
                onValueChange = { addressFindText = it },
                placeholder = {
                    Text(
                        text = "상세주소",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )
            TextField(
                value = birthText,
                onValueChange = { birthText = it },
                placeholder = {
                    Text(
                        text = "생년월일",
                        color = Color(0xFFC4C4C4)
                    )
                },
            )

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonJoinin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(280.dp)
                    .height(46.dp)
            )
            {
                Text(text = "회원가입하기", color = Color.White)
            }
        }
    }

    fun ButtonJoinin(){
        val intent = Intent(this@JoininView, Map::class.java)
        intent.putExtra("UserEmail", "testingEmail")
        startActivity(intent)
    }
}