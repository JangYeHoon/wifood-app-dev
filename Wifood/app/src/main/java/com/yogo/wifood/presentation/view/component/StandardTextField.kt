package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.view.ui.theme.Enable

@Composable
fun StandardTextField(
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