package com.yogo.wifood.presentation.view.placeList.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun PlaceReviewInputText(
    text:String = "",
    placeholder:String = "맛집 리뷰",
    lengthText:String = "0/500",
    onValueChange: (String) -> Unit = {},
){
    Box(
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = Color(0xFFC4C4C4),
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            maxLines = 5,
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MainColor,
                unfocusedBorderColor = Color(0xFFF1F1F1),
                cursorColor = Color.Transparent,
                textColor = Gray01Color,
            ),
            textStyle = TextStyle(
                color = Gray01Color,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
        Text(
            text = lengthText,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp)
                .padding(bottom = 9.dp),
            color = Color(0xFFC4C4C4)
        )
    }
}