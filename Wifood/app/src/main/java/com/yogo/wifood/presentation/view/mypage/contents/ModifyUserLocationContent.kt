package com.yogo.wifood.presentation.view.mypage.contents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.YOGOBasicText
import com.yogo.wifood.presentation.view.login.ClickableTextFieldForm1
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue

//Preview(showBackground = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserLocationContent(
    onTextFieldClicked: () -> Unit = {},
    onTextFieldValueChanged: (String) -> Unit = {},
    onModifyButtonClicked: () -> Unit = {},
    activateButton: Boolean = false
) {
    val scrollState = rememberScrollState() // for horizontal mode screen

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
                text = MainData.user.address,
                onClick = onTextFieldClicked,
                onValueChange = onTextFieldValueChanged
            )
            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "변경하기",
                onClick = onModifyButtonClicked,
                activate = activateButton
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}