package com.example.wifood.presentation.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.*

@Composable
fun SignUpView3(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ){
            Spacer(Modifier.weight(1f))
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_1by4),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "동네를 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "동명(읍,면)으로 검색",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Gray03Color
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    navController.navigate(Route.FindLocation.route)
                }
            ){
                TextField(
                    value = SignUpData.address,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.AddressChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            navController.navigate(Route.FindLocation.route)
                        },
                    textStyle = TextStyle(
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Gray01Color
                    ),
                    placeholder = {
                        Text(
                            text = "중앙동",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            color = EnableColor
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = MainColor,
                        textColor = Gray01Color,
                        placeholderColor = EnableColor,
                        focusedIndicatorColor = MainColor,
                        unfocusedIndicatorColor = EnableColor
                    ),
                    enabled = false
                )
            }
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = {
                    navController.navigate(Route.SignUp4.route)
                },
                activate = SignUpData.address.isNotEmpty()
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}