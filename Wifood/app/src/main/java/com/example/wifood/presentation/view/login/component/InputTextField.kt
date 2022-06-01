package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.RoundedTextFieldFocusColor
import com.example.wifood.view.ui.theme.RoundedTextFieldUnFocusColor

@Composable
fun InputTextField(
    text:String = "",
    placeholder:String= "아이디",
    onValueChange:(String)->Unit = {},
    height:Int = 50,
    isPassword:Boolean = false,
){
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = EnableColor
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().height(height.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Gray01Color,
            backgroundColor = Color.White,
            cursorColor = Color.Transparent,
            focusedBorderColor = RoundedTextFieldFocusColor,
            unfocusedBorderColor = RoundedTextFieldUnFocusColor
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None
    )
}