package com.example.wifood.presentation.view.mypage.NewMypageComposeView

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOBasicText
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.presentation.view.login.ClickableTextFieldForm1
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserLocationView(
) {
    val scrollState = rememberScrollState() // for horizontal mode screen

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
            YOGOBasicText(
                largeText = "새로운 동네를\n알려주세요.",
                explainText = "동, 읍까지만 입력해주세요."
            )
            Spacer(Modifier.height(24.dp))
            ClickableTextFieldForm1(
                text = "",//SignUpData.address,
                onClick = {
                    //navController.navigate(Route.FindLocation.route)
                },
                onValueChange = {
                    //viewModel.onEvent(SignUpEvent.AddressChanged(it))
                }
            )
            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "변경하기",
                onClick = {
                    //navController.navigate(Route.SignUp4.route)
                },
                activate = SignUpData.address.isNotEmpty()
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}