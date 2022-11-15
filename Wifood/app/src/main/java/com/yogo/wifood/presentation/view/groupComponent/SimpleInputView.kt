package com.yogo.wifood.presentation.view.groupComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.login.util.phoneFilter
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SimpleInputView(
    explainText: String = "",
    textFieldText: String = "",
    onTextFieldValueChanged: (String) -> Unit = {},
    onTextFieldValueReset: () -> Unit = {},
    placeholderText: String = "placeholder",
    buttonText: String,
    onButtonClick: () -> Unit,
    buttonActivate: Boolean
) {
    val scrollState = rememberScrollState()
    val interactionSource = MutableInteractionSource()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = Color.White)
                .padding(horizontal = sidePaddingValue.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = explainText,
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(24.dp))
            TextField(
                value = textFieldText,
                onValueChange = onTextFieldValueChanged,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Gray01Color
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
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = MainColor,
                    textColor = Gray01Color,
                    placeholderColor = EnableColor,
                    focusedIndicatorColor = MainColor,
                    unfocusedIndicatorColor = EnableColor
                ),
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    //keyboardType = KeyboardType.Phone
                ),
                trailingIcon = {
                    if (textFieldText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 10.dp)
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
                                        onTextFieldValueReset()
                                        textFieldText.none()
                                    },
                                tint = Color.Unspecified,
                            )
                        }
                    }
                }
            )
            Spacer(Modifier.weight(1f))
            MainButton(
                text = buttonText,
                onClick = onButtonClick,
                activate = buttonActivate
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}

