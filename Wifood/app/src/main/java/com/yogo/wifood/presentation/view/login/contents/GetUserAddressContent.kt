package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.YOGOBasicText
import com.yogo.wifood.presentation.view.login.ClickableTextFieldForm1
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue

//@Preview(showBackground = true)
@Composable
fun GetUserAddressContent(
    addressText: String = "",
    onAddressClicked: () -> Unit = {},
    onAddressValueChanged: (String) -> Unit = {},
    onButtonClicked: () -> Unit = {},
    activateButton: Boolean = false
){
    val scrollState = rememberScrollState()

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
            Spacer(Modifier.height(106.dp))
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_1by4),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(10.dp))
            YOGOBasicText(
                largeText = "동네를 알려주세요",
                explainText = "동명(읍,면)으로 검색"
            )
            Spacer(Modifier.height(24.dp))
            ClickableTextFieldForm1(
                text = addressText,
                onClick = onAddressClicked,
                onValueChange = onAddressValueChanged
            )
            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = onButtonClicked,
                activate = activateButton
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}