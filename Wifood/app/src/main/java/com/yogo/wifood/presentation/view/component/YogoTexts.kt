package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.Gray03Color

@Composable
fun YOGOLargeText(
    text: String = "맛집 리뷰를 등록해주세요",
    color:Color = Black2Color,
    textAlign:TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = color,
        textAlign = textAlign
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

@Composable
fun YOGOPR16_title(
    text:String = "맛집 주소 등록",
    color:Color = Color.Black
){
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = color
    )

}

@Composable
fun YOGOPR12_formInfo(
    text: String = "지도를 움직여 위치를 설정해주세요",
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
){
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = color,
        textAlign = textAlign
    )
}