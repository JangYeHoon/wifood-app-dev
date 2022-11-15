package com.yogo.wifood.presentation.view.search.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color

//@Preview(showBackground = true)
@Composable
fun NoSearchResultContent(
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
            .padding(
                top = 56.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "검색결과가 없습니다.\n동네 이름을 다시 확인해주세요.",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Gray01Color,
            textAlign = TextAlign.Center
        )
    }
}
