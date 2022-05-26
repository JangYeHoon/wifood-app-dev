package com.example.wifood.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.PrimaryTextButton
import com.example.wifood.presentation.view.component.StandardTextField
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Error
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wifood.presentation.view.login.component.LogoImage

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    navController.navigate(Route.Map.route)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(39.dp))
            StandardTextField(
                label = "아이디",
                text = state.email,
                placeholder = "아이디를 입력해주세요.",
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                keyboardType = Email,
                isError = state.emailError != null
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = Error
                )
            }
            StandardTextField(
                label = "비밀번호",
                text = state.password,
                placeholder = "비밀번호를 입력해주세요.",
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                keyboardType = Password,
                isError = state.passwordError != null
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = Error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryTextButton(
                text = "로그인",
                onClick = {
                    viewModel.onEvent(LoginFormEvent.Login)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContent(){
    val idText = ""
    Scaffold(

    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoImage()
            Spacer(Modifier.height(50.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .height(50.dp)
                    .width(280.dp)
                ,
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "아이디",
                        color = Color(0xFFCFCFCF),
                        fontSize = 14.sp,
                        fontFamily = fontPretendard,
                        fontWeight = FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(23.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0xFF565656),
                    backgroundColor = Color.White,
                    cursorColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),
                singleLine = true,
            )
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .height(50.dp)
                    .width(280.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "비밀번호",
                        color = Color(0xFFCFCFCF),
                        fontSize = 14.sp,
                        fontFamily = fontPretendard,
                        fontWeight = FontWeight.Normal
                    )
                },

                shape = RoundedCornerShape(23.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0xFF565656),
                    backgroundColor = Color.White,
                    cursorColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFEA7434),
                    unfocusedBorderColor = Color(0xFFE4E4E4)
                ),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(10.dp))
            TextButton(
                shape = RoundedCornerShape(23.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEA7434)
                ),
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp)
            )
            {
                Text(
                    text = "로그인",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    fontFamily = fontPretendard,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(10.dp))
            Row() {
                TextButton(
                    shape = RoundedCornerShape(23.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp)
                )
                {
                    Text(
                        text = "아이디/비밀번호찾기",
                        color = Color(0xFF979797),
                        fontSize = 12.sp,
                        fontFamily = fontPretendard,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(Modifier.width(5.dp))
                TextButton(
                    shape = RoundedCornerShape(23.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                ){
                    Text(
                        text = "회원가입",
                        color = Color(0xFF565656),
                        fontSize = 12.sp,
                        fontFamily = fontPretendard,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            Divider(
                color = Color(0xFFE4E4E4),
                modifier = Modifier
                    .width(280.dp)
                    .height(1.dp)
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "SNS 계정으로 간편 로그인/회원가입",
                color = Color(0xFF565656),
                fontSize = 12.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(18.dp))
            Row(){
                IconButton(
                    onClick = {}, // TODO(naver login)
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ){
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_naver_login_icon),
                        contentDescription = "Login by naver api",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified
                    )
                }
                Spacer(Modifier.width(20.dp))
                IconButton(
                    onClick = {}, // TODO(kakao login)
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ){
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_kakao_login_icon),
                        contentDescription = "Login by kakao api",
                        tint = Color.Unspecified
                    )
                }
                Spacer(Modifier.width(20.dp))
                IconButton(
                    onClick = {}, // TODO(google login)
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ){
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_google_login_icon),
                        contentDescription = "Login by google api",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}