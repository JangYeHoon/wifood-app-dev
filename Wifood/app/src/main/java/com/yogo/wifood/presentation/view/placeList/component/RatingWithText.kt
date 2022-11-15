package com.yogo.wifood.presentation.view.groupComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogo.wifood.presentation.view.component.YOGORatingStar
import com.yogo.wifood.presentation.view.login.component.TitleText

@Composable
fun RatingWithText(
    text: String = "어떤 점이 가장 좋았나요?",
    selectedArray: List<Int>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(text = text)
        Spacer(Modifier.height(5.dp))
        YOGORatingStar(selectedArray)
    }
}