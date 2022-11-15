package com.yogo.wifood.presentation.view.login.component

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@Composable
fun InputTextField(
    text: String = "",
    placeholder: String = "아이디",
    onValueChange: (String) -> Unit = {},
    height: Int = 50,
    isPassword: Boolean = false,
    maxLine: Int = 1,
    resetIconOffset: Int = 10,
    enabled: Boolean = true,
    onValueReset: () -> Unit = {}
) {

    var textFieldText = text
    var isFocused = false
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var current_focus = false
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column() {
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                maxLines = maxLine,
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Gray01Color,
                    textDecoration = TextDecoration.None,
                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterStart)
                    .focusRequester(focusRequester),
                visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None,
                cursorBrush = SolidColor(MainColor),
                decorationBox = { innerTextField ->
                    Row(
                    )
                    {
                        if (text.isEmpty()) {

                            Text(
                                text = placeholder,
                                color = EnableColor,
                                fontFamily = mainFont,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                            //TODO("set cursor location to zero")
                        }
                        innerTextField()
                    }
                },
                enabled = enabled
            )
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = {
                    },
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = resetIconOffset.dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_reset_text_button),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()

                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                onValueReset()
                                text.none()
                            },
                        tint = Color.Unspecified,
                    )
                }
            }
        }
        Spacer(Modifier.height(5.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = if (text.isNotEmpty()) MainColor else EnableColor
        )
        Spacer(Modifier.height(5.dp))
    }
}