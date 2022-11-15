package com.yogo.wifood.presentation.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.MainColor


@Composable
fun ClickableTextFieldForm1(
    text:String = "",
    onClick:()->Unit = {},
    onValueChange:(String) -> Unit = {}
){
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            onClick()
        }
    ){
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            textStyle = TextStyle(
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Gray01Color
            ),
            placeholder = {
                Text(
                    text = "서초동",
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
            ),
            enabled = false
        )
    }
}