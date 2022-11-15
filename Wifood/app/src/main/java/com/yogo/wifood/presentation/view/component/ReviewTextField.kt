package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
fun ReviewTextField(
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "맛집 리뷰",
    modifier: Modifier = Modifier
        .height(120.dp),
    fontSize:Int = 15,
    showCount: Boolean = true,
    enabled: Boolean = true
) {
    Box(

    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize.sp,
                    color = Color(0xFFC4C4C4)
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(5.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                cursorColor = MainColor
            ),
            textStyle = TextStyle(
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize.sp,
                color = Gray01Color
            ),
            enabled = enabled
        )
        if (showCount){
            Text(
                text = text.length.toString() + "/500",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 14.dp, bottom = 10.dp),
                color = Color(0xFFC4C4C4)
            )
        }
    }
}