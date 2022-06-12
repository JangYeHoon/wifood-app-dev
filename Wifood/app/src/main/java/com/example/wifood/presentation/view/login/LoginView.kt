package com.example.wifood.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.ui.theme.fontPretendard
import com.example.wifood.ui.theme.*
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.launch

//@Preview(showBackground=true)
@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val formState = viewModel.formState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    formState.clear()
                    navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LogoImage(
                width = 86,
                height = 28
            )
            Spacer(Modifier.height(50.dp))
            RoundedTextField(
                text = formState.email,
                placeholder = "아이디",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                    }
                }
            )
            Spacer(Modifier.height(5.dp))
            RoundedTextField(
                text = formState.password,
                placeholder = "비밀번호",
                isPassword = true,
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            MainButton(
                text = "로그인",
                onClick = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.Login)
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            Row() {
                TransparentButton(
                    text = "아이디/비밀번호찾기",
                    textColor = Gray03Color,
                    onClick = {/*TODO*/}
                )
                Spacer(Modifier.width(5.dp))
                TransparentButton(
                    text = "회원가입",
                    textColor = Gray01Color,
                    onClick = {
                        formState.clear()
                        navController.navigate(Route.MobileAuthentication.route)
                    }
                )
            }
            Spacer(Modifier.height(40.dp))
            Divider(
                color = DividerColor,
                modifier = Modifier
                    .fillMaxWidth()
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