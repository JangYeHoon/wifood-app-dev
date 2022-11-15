package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun RoundedTextField(
    text: String,
    placeholder: String,
    isPassword: Boolean = false,
    height: Int = 50,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction? = ImeAction.Next,
    isError: Boolean = false
) {
    val focus = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            //.height(height.dp)
        ,
        maxLines = 1,
        placeholder = {
            Text(
                text = placeholder,
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
            cursorColor = MainColor,
            focusedBorderColor = RoundedTextFieldFocusColor,
            unfocusedBorderColor = RoundedTextFieldUnFocusColor
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None,
        keyboardActions = KeyboardActions(
            onNext = {
                focus.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focus.clearFocus()
                scope.launch {

                }
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction ?: ImeAction.Next,
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        isError = isError
    )
}