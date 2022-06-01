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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.component.LogoImage
import com.example.wifood.presentation.view.login.component.RoundedTextField
import com.example.wifood.presentation.view.login.component.SnsIconButton
import com.example.wifood.presentation.view.login.component.TransparentButton
import com.example.wifood.ui.theme.fontPretendard
import com.example.wifood.ui.theme.*
import com.example.wifood.view.ui.theme.*

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
//@Preview(showBackground=true)
@Composable
fun LoginContent(){

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoImage()
            Spacer(Modifier.height(50.dp))
            RoundedTextField(
                text="", /*TODO*/
                placeholder = "아이디",
                onValueChange = {/*TODO*/}
            )
            Spacer(Modifier.height(5.dp))
            RoundedTextField(
                text="",/*TODO*/
                placeholder = "비밀번호",
                isPassword = true,
                onValueChange = {/*TODO*/}
            )
            Spacer(Modifier.height(10.dp))
            MainButton(
                text="로그인",
                width = 280,
                height = 50,
                onClick = {/*TODO*/}
            )
            Spacer(Modifier.height(10.dp))
            Row() {
                TransparentButton(
                    text="아이디/비밀번호찾기",
                    textColor = Gray03Color,
                    textSize = 12,
                    width = 150,
                    height = 30,
                    onClick = {/*TODO*/}
                )
                Spacer(Modifier.width(5.dp))
                TransparentButton(
                    text="회원가입",
                    textColor = Gray01Color,
                    textSize = 12,
                    width = 80,
                    height = 30,
                    onClick={/*TODO*/}
                )
            }
            Spacer(Modifier.height(40.dp))
            Divider(
                color = DividerColor,
                modifier = Modifier
                    .width(280.dp)
                    .height(1.dp)
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "SNS 계정으로 간편 로그인/회원가입",
                color = Gray01Color,
                fontSize = 12.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(18.dp))
            Row(){
                SnsIconButton(
                    resourceId = R.drawable.ic_naver_login_icon,
                    onClick = {/*TODO*/}
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_kakao_login_icon,
                    onClick = {/*TODO*/}
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_google_login_icon,
                    onClick = {/*TODO*/}
                )
            }
        }
    }
}