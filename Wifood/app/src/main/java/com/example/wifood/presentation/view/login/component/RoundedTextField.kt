package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.login.fontPretendard
import com.example.wifood.view.ui.theme.Enable

@Composable
fun RoundedTextField(
    text: String,
    color: Color,

){
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .height(50.dp)
            .width(280.dp)
        ,
        maxLines = 1,
        placeholder = {
            Text(
                text = text,
                color = color,
                fontSize = 14.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
        },
        shape = RoundedCornerShape(23.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color(0xFF565656),
            backgroundColor = Color.White,
            cursorColor = Color.Transparent,
            focusedBorderColor = Color(0xFFEA7434),
            unfocusedBorderColor = Color(0xFFE4E4E4)
        ),
        singleLine = true,
    )
}

@Composable
fun StandardTextField2(
    label: String,
    description: String? = null,
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    isPassword: Boolean = keyboardType == KeyboardType.Password
) {
    Text(
        text = label,
        fontSize = 15.sp,
        color = Color(0xFF424242)
    )
    if (!description.isNullOrBlank()) {
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color(0xFF565656)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                color = Enable
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        isError = isError,
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None
    )
}