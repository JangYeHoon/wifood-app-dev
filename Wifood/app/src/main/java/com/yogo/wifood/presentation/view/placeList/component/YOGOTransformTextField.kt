package com.yogo.wifood.presentation.view.placeList.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.login.util.numberCommaFilter
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun YOGOTransformTextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    selectable: Boolean = false,
    selectFunction: () -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    if (selectable)
                        selectFunction()
                },
            textStyle = TextStyle(
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Gray01Color,
            ),
            placeholder = {
                Text(
                    text = placeholderText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = EnableColor
                )
            },
            enabled = !selectable,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = MainColor,
                textColor = Gray01Color,
                placeholderColor = EnableColor,
                focusedIndicatorColor = MainColor,
                unfocusedIndicatorColor = EnableColor
            ),
            visualTransformation = {
                numberCommaFilter(it)
            },
            keyboardOptions = KeyboardOptions(
                //keyboardType = KeyboardType.Phone
            ),
            trailingIcon = {
                if (selectable) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                selectFunction()
                            },
                        tint = Color.Unspecified
                    )
                }
            }
        )
    }
}