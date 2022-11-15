package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.Main
import com.yogo.wifood.view.ui.theme.MainColor

val mainButtonRoundValue = 23
val mainButtonHeightValue = 46

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    activate: Boolean = true,
) {

    TextButton(
        shape = RoundedCornerShape(mainButtonRoundValue.dp),
        onClick = onClick,
        enabled = activate,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (activate) MainColor else EnableColor,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(mainButtonHeightValue.dp)
    )
    {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MainButtonInversed(
    text: String = "인증번호 재발송",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(23.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(mainButtonHeightValue.dp),
        border = BorderStroke(1.dp, MainColor)
    ) {
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MainColor
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainButtonToggle(
    text: String = "전체",
    onClick: () -> Unit = {},
    isClicked: Boolean = true,
    modifier: Modifier = Modifier,
    buttonColor: Color = MainColor,
    borderColor: Color = MainColor
) {
    val cornerValue = 32
    if (isClicked.not()) {
        TextButton(
            onClick = onClick,
            shape = RoundedCornerShape(cornerValue.dp),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            border = BorderStroke(
                1.dp, borderColor
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = buttonColor
            )
        ) {
            Spacer(Modifier.width(14.dp))
            Text(
                text = text,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = borderColor
            )
            Spacer(Modifier.width(14.dp))
        }
    } else {
        TextButton(
            shape = RoundedCornerShape(cornerValue.dp),
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = buttonColor
            ),
            enabled = true,
            border = BorderStroke(
                1.dp, borderColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        {
            Spacer(Modifier.width(14.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.width(14.dp))
        }
    }
}