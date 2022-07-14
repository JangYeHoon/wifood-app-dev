package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.component.*
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.ui.theme.fontPretendard
import com.example.wifood.util.Constants
import com.example.wifood.util.Constants.INVALID
import com.example.wifood.util.Constants.VALID
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    val autoLogin = remember {
        WifoodApp.pref.getString("auto_login", INVALID)
    }

    LaunchedEffect(key1 = autoLogin) {
        when (autoLogin) {
            VALID -> {
                navController.navigate(Route.Main.route)
            }
        }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    WifoodApp.pref.setString("user_id", viewModel.formState.email)
                    WifoodApp.pref.setString("auto_login", VALID)
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
        if (formState.emailError.isNotBlank()) {
            LoginErrorText(formState.emailError, true)
        } else if (formState.passwordError.isNotBlank()) {
            LoginErrorText(formState.passwordError, true)
        }

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
                },
                isError = formState.emailError.isNotBlank()
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
                },
                imeAction = ImeAction.Done,
                isError = formState.passwordError.isNotBlank()
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
            Spacer(Modifier.height(5.dp))
            Row() {
                TransparentButton(
                    text = "아이디/비밀번호찾기",
                    textColor = Gray03Color,
                    onClick = {
                        formState.clear()
                        navController.navigate(Route.MobileAuthentication.route)
                    }
                )
                Spacer(Modifier.width(10.dp))
                TransparentButton(
                    text = "회원가입",
                    textColor = Gray01Color,
                    onClick = {
                        formState.clear()
                        navController.navigate(Route.MobileAuthentication.route)
                    }
                )
            }
            Spacer(Modifier.height(49.dp))
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
            Row() {
                SnsIconButton(
                    resourceId = R.drawable.ic_naver_login_icon,
                    onClick = {/*TODO*/ }
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_kakao_login_icon,
                    onClick = {/*TODO*/ }
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_google_login_icon,
                    onClick = {/*TODO*/ }
                )
            }
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}