package com.example.wifood.presentation.view.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Gray03Color

@Composable
fun YOGOLargeText(
    text: String = "맛집 리뷰를 등록해주세요"
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = Black2Color
    )
}

@Composable
fun YOGOExplainText(
    text: String = "맛집리뷰와 사진을 등록해주세요"
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Gray03Color
    )
}

@Composable
fun YOGOBasicText(
    largeText: String = "",
    explainText: String = ""
) {
    YOGOLargeText(text = largeText)
    if (explainText.isNotEmpty()) {
        Spacer(Modifier.height(12.dp))
        YOGOExplainText(text = explainText)
    }
}

@Composable
fun YOGOTextPM15(
    text:String = "",
    buildText: AnnotatedString = buildAnnotatedString { append("") }
){
    if (buildText.text.isEmpty()) {
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Gray01Color
        )
    } else {
        Text(
            text = buildText,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Gray01Color
        )
    }
}