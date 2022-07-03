package com.example.wifood.presentation.view.login.component

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*

@Composable
fun InputTextField2(
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
                color = EnableColor,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
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
        visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None,
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Gray01Color
        )
    )
}

@Composable
fun InputTextField(
    text:String = "",
    placeholder: String = "아이디",
    onValueChange: (String) -> Unit = {},
    height: Int = 50,
    isPassword: Boolean = false,
    maxLine:Int = 1,
    resetIconOffset:Int = 10,
){
    var text = text

    Column(){
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier.fillMaxWidth()
        ){
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                maxLines = maxLine,
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Gray01Color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterStart),
                visualTransformation = if (isPassword) PasswordVisualTransformation('*') else VisualTransformation.None,
                cursorBrush = if (text.isEmpty()) SolidColor(Color.Transparent) else SolidColor(MainColor),
                decorationBox = { innerTextField ->
                    Row(
                    )
                    {
                        if (text.isEmpty()){
                            Text(
                                text = placeholder,
                                color = EnableColor,
                                fontFamily = mainFont,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                }
            )
            IconButton(
                onClick = {text = ""},
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            ){
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_reset_text_button),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = if (!text.isEmpty()) MainColor else EnableColor
        )
        Spacer(Modifier.height(5.dp))
    }
}