package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.presentation.view.login.contents.UserFavorButtonGroup
import com.example.wifood.presentation.view.login.contents.UserFavorRadioGroup
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.buttonBottomValue

@Composable
fun ModifyTasteFavorContent(
    favorSelected: (Int, Int) -> Unit = { _, _ -> },
    onClickChangeTasteButton: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(106.dp))
        YOGOLargeText(
            text = "입맛을 알려주세요."
        )
        Spacer(Modifier.height(36.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(36.dp))
            UserFavorRadioGroup(favorSelected)
            Text(
                text = "좋아하면 선택해주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(16.dp))
            UserFavorButtonGroup()
        }
        Spacer(Modifier.height(130.dp))
        Spacer(Modifier.weight(1f))
        MainButton(
            text = "변경하기",
            onClick = onClickChangeTasteButton
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}