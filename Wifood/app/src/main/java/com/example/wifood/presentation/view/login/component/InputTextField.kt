package com.example.wifood.presentation.view.login.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.view.ui.theme.*

@Composable
fun InputTextField(
    text: String = "",
    placeholder: String = "아이디",
    onValueChange: (String) -> Unit = {},
    height: Int = 50,
    isPassword: Boolean = false,
    maxLine:Int = 1,
    resetIconOffset:Int = 10,
) {
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
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Gray01Color,
            backgroundColor = Color.White,
            cursorColor = MainColor,
            focusedBorderColor = RoundedTextFieldFocusColor,
            unfocusedBorderColor = RoundedTextFieldUnFocusColor
        ),
        trailingIcon = {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_reset_text_button),
                contentDescription = "clear text",
                modifier = Modifier
                    .offset(x = resetIconOffset.dp)
                    .clickable {
                        onValueChange("")
                    }
                    .wrapContentSize(),
                tint = Color.Unspecified
            )
        },
        maxLines = maxLine,
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None
    )
}