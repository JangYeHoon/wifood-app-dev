package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.login.component.ErrorText
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.login.component.TextInsideButton
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.join.JoininEvent
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.launch

@Composable
fun MobileAuthenticationView(
    navController: NavController,
    from:String = "findIdAndPwd"
) {

    val scaffoldState = rememberScaffoldState()
    var AuthNum:String = ""
    var AuthInputNum by remember { mutableStateOf("") }
    var AuthCheckNum by remember { mutableStateOf("")}
    var AuthPass:Boolean = true

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "본인인증",
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp)
                .padding(top = 18.dp)
        )
        {
            // Set phone check
            TitleText("본인인증")
            Spacer(Modifier.height(5.dp))
            //ExplainText("")
            Spacer(Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                InputTextField(
                    text = AuthInputNum,
                    placeholder = "휴대폰 번호('-' 제외)",
                    onValueChange = {
                        AuthInputNum = it
                    },
                    resetIconOffset = 40,
                )
                TextInsideButton(
                    text = "인증번호 받기",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {},
                )
            }
            ErrorText(
                text = "올바른 핸드폰 번호를 입력해주세요",
                visibility = false,
            )
            InputTextField(
                text = AuthCheckNum,
                placeholder = "인증번호 입력 (3분 이내)",
                onValueChange = {
                    AuthCheckNum = it
                },
            )
            ErrorText(
                text = "인증번호와 일치하지 않습니다. 다시 시도해주세요",
                visibility = false,
            )
            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            MainButton(
                text = "본인인증",
                onClick = {
                    if (from.equals("Joinin"))
                        navController.navigate(Route.Joinin.route)
                    else if (from.equals("findIdAndPwd"))
                        navController.navigate(Route.Joinin.route)
                },
                activate = AuthPass
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }

}