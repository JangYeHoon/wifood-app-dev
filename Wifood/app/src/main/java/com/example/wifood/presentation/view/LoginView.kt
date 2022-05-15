package com.example.wifood.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.wifood.R
import com.example.wifood.activity.Map

class LoginView : ComponentActivity() {

    val fontPretendard = FontFamily(
        Font(R.font.pbold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.pmedium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.pregular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.plight, FontWeight.Light, FontStyle.Normal),
        Font(R.font.pthin, FontWeight.Thin, FontStyle.Normal),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutContent()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ConstraintLayoutContent() {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {

            // create refs
            val (image_logo, text_id, text_pwd, button_login, button_find_idOrPwd, button_joinin, line, text_simpleLogin, button_naverLogin,button_kakaoLogin,button_googleLogin) = createRefs()
            var idText by remember { mutableStateOf(TextFieldValue("")) }
            var pwdText by remember { mutableStateOf(TextFieldValue("")) }

            Image(
                painter = painterResource(id = R.drawable.ic_login_logo),
                contentDescription = "login Logo",
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(25.dp)
                    .width(75.dp)
                    .constrainAs(image_logo) {
                        top.linkTo(parent.top, margin = 120.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            OutlinedTextField(
                value = idText,
                onValueChange = {
                    idText = it
                },
                maxLines=1,
                placeholder = {
                    Text(
                        text = "아이디",
                        color = Color(0xFFCFCFCF),
                        fontSize = 14.sp,
                        fontFamily= fontPretendard,
                        fontWeight = FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(23.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = White,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),

                modifier = Modifier
                    .width(280.dp)
                    .height(46.dp)
                    .constrainAs(text_id){
                        top.linkTo(image_logo.bottom,margin= 53.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            OutlinedTextField(
                value = pwdText,
                onValueChange = {
                    pwdText = it
                },
                placeholder = {
                    Text(
                        text = "비밀번호",
                        color = Color(0xFFC4C4C4)
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = White,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),
                modifier = Modifier
                    .width(280.dp)
                    .height(46.dp)
                    .constrainAs(text_pwd){
                        top.linkTo(text_id.bottom,margin= 5.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(280.dp)
                    .height(46.dp)
                    .constrainAs(button_login){
                        top.linkTo(text_pwd.bottom,margin= 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            {
                Text(text = "로그인", color = White)
            }

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonFindIdOrPwd()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(98.dp)
                    .height(14.dp)
                    .constrainAs(button_find_idOrPwd){
                        top.linkTo(button_login.bottom,margin= 18.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end,margin=165.dp)
                    }
            )
            {
                Text(text = "아이디/비밀번호찾기", color = White)
            }

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonJoinin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(98.dp)
                    .height(14.dp)
                    .constrainAs(button_joinin){
                        top.linkTo(button_login.bottom,margin= 18.dp)
                        start.linkTo(parent.start,margin=220.dp)
                        end.linkTo(parent.end)
                    }
            )
            {
                Text(text = "회원가입", color = White)
            }


            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonNaverLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .constrainAs(button_naverLogin){
                        top.linkTo(button_find_idOrPwd.bottom,margin= 106.dp)
                        start.linkTo(parent.start,margin=97.dp)
                        end.linkTo(parent.end)
                    }
            )
            {
                Text(text = "N", color = White)
            }

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonNaverLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .constrainAs(button_kakaoLogin){
                        top.linkTo(button_find_idOrPwd.bottom,margin= 106.dp)
                        start.linkTo(parent.start,margin=159.dp)
                        end.linkTo(parent.end)
                    }
            )
            {
                Text(text = "K", color = White)
            }

            TextButton(
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    ButtonNaverLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .constrainAs(button_googleLogin){
                        top.linkTo(button_find_idOrPwd.bottom,margin= 106.dp)
                        start.linkTo(parent.start,margin=221.dp)
                        end.linkTo(parent.end)
                    }
            )
            {
                Text(text = "K", color = White)
            }




        }
    }

    fun ButtonLogin() {
        val intent = Intent(this@LoginView, Map::class.java)
        intent.putExtra("UserEmail", "testingEmail")
        startActivity(intent)
        Toast.makeText(this, "go to map", Toast.LENGTH_SHORT).show()
    }

    fun ButtonJoinin() {
        val intent = Intent(this@LoginView, JoininView::class.java)
        intent.putExtra("UserEmail", "testingEmail")
        startActivity(intent)
    }

    fun ButtonFindIdOrPwd() {
        val intent = Intent(this@LoginView, FindIdOrPwdView::class.java)
        intent.putExtra("UserEmail", "testingEmail")
        startActivity(intent)
    }

    fun ButtonNaverLogin() {
        Toast.makeText(this, "not implemented", Toast.LENGTH_SHORT).show()
    }
    fun ButtonKakaoLogin() {
        Toast.makeText(this, "not implemented", Toast.LENGTH_SHORT).show()
    }
    fun ButtonGoogleLogin() {
        Toast.makeText(this, "not implemented", Toast.LENGTH_SHORT).show()
    }
}