package com.example.wifood.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Error
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Don't touch
        val state = viewModel.state
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collectLatest { event ->
                when(event) {
                    is LoginViewModel.ValidationEvent.Success -> {
                        navController.navigate(Route.Map.route)
                    }
                }
            }
        }

        // create refs
        val (image_logo, text_id, text_pwd, button_login, button_find_idOrPwd, button_joinin, line, text_simpleLogin, button_naverLogin, button_kakaoLogin, button_googleLogin) = createRefs()
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
            value = state.email,
            onValueChange = {
                viewModel.onEvent(LoginFormEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            maxLines = 1,
            placeholder = {
                Text(
                    text = "아이디",
                    color = Color(0xFFCFCFCF),
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
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
                .constrainAs(text_id) {
                    top.linkTo(image_logo.bottom, margin = 53.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = Email
            )
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = Error
            )
        }
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
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
                .constrainAs(text_pwd) {
                    top.linkTo(text_id.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = Error
            )
        }

        TextButton(
            shape = RoundedCornerShape(25.dp),
            onClick = {
                viewModel.onEvent(LoginFormEvent.Login)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(280.dp)
                .height(46.dp)
                .constrainAs(button_login) {
                    top.linkTo(text_pwd.bottom, margin = 10.dp)
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

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(98.dp)
                .height(14.dp)
                .constrainAs(button_find_idOrPwd) {
                    top.linkTo(button_login.bottom, margin = 18.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 165.dp)
                }
        )
        {
            Text(text = "아이디/비밀번호찾기", color = White)
        }

        TextButton(
            shape = RoundedCornerShape(25.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(98.dp)
                .height(14.dp)
                .constrainAs(button_joinin) {
                    top.linkTo(button_login.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = 220.dp)
                    end.linkTo(parent.end)
                }
        )
        {
            Text(text = "회원가입", color = White)
        }


        TextButton(
            shape = RoundedCornerShape(25.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .constrainAs(button_naverLogin) {
                    top.linkTo(button_find_idOrPwd.bottom, margin = 106.dp)
                    start.linkTo(parent.start, margin = 97.dp)
                    end.linkTo(parent.end)
                }
        )
        {
            Text(text = "N", color = White)
        }

        TextButton(
            shape = RoundedCornerShape(25.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .constrainAs(button_kakaoLogin) {
                    top.linkTo(button_find_idOrPwd.bottom, margin = 106.dp)
                    start.linkTo(parent.start, margin = 159.dp)
                    end.linkTo(parent.end)
                }
        )
        {
            Text(text = "K", color = White)
        }

        TextButton(
            shape = RoundedCornerShape(25.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFEA7434)
            ),
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .constrainAs(button_googleLogin) {
                    top.linkTo(button_find_idOrPwd.bottom, margin = 106.dp)
                    start.linkTo(parent.start, margin = 221.dp)
                    end.linkTo(parent.end)
                }
        )
        {
            Text(text = "K", color = White)
        }


    }
}