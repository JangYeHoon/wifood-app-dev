package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.example.wifood.view.ui.theme.Enable

@Composable
fun RoundedTextField(
    text: String,
    placeholder:String,
    isPassword: Boolean = false,
    height:Int = 50,
    onValueChange: (String) -> Unit,
    ){
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
        ,
        maxLines = 1,
        placeholder = {
            Text(
                text = text,
                color = EnableColor,
                fontSize = 14.sp,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal
            )
        },
        shape = RoundedCornerShape(23.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = RoundedTextFieldTextColor,
            backgroundColor = Color.White,
            cursorColor = Color.Transparent,
            focusedBorderColor = RoundedTextFieldFocusColor,
            unfocusedBorderColor = RoundedTextFieldUnFocusColor
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None
    )
}