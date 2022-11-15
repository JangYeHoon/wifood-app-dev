package com.yogo.wifood.presentation.view.placeList.componentGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.MainButtonInversed
import com.yogo.wifood.presentation.view.component.mainButtonHeightValue
import com.yogo.wifood.presentation.view.component.mainButtonRoundValue
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun DoubleButton(
    leftButtonOn: Boolean = false,
    leftButtonText: String = "건너뛰기",
    leftButtonClicked : () -> Unit = {},
    rightButtonOn: Boolean = false,
    rightButtonText : String = "메뉴 평가하기",
    rightButtonClicked : () -> Unit = {},
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){

        OutlinedButton(
            onClick = leftButtonClicked,
            shape = RoundedCornerShape(23.dp),
            modifier = Modifier
                .weight(0.4f)
                .height(mainButtonHeightValue.dp),
            border = BorderStroke(1.dp, MainColor),
            enabled = leftButtonOn
        ) {
            Text(
                text = leftButtonText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MainColor
            )
        }
        Spacer(Modifier.width(8.dp))
        TextButton(
            shape = RoundedCornerShape(mainButtonRoundValue.dp),
            onClick = rightButtonClicked,
            enabled = rightButtonOn,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainColor,
            ),
            modifier = Modifier
                .weight(0.6f)
                .height(mainButtonHeightValue.dp)
        )
        {
            Text(
                text = rightButtonText,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold
            )
        }
    }
}