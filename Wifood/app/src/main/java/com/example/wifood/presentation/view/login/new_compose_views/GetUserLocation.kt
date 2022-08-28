package com.example.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.presentation.view.login.util.ViewItem
import com.example.wifood.presentation.view.login.util.phoneFilter
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetUserLocation(
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen
    var userLocationActivated = false

    Scaffold(
        scaffoldState = scaffoldState
    ) {
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
                TextField(
                    value = "",
                    onValueChange = {
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    )
                )
                Spacer(Modifier.weight(1f))
                MainButton(
                    text = "다음",
                    onClick = {
                    },
                    activate = userLocationActivated
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}
