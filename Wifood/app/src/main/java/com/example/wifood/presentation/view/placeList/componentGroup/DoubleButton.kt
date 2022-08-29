package com.example.wifood.presentation.view.placeList.componentGroup

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
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.MainButtonInversed
import com.example.wifood.presentation.view.component.mainButtonHeightValue
import com.example.wifood.presentation.view.component.mainButtonRoundValue
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun DoubleButton(
    leftButtonText : String = "건너뛰기",
    leftButtonClicked : () -> Unit = {},
    rightButtonText : String = "메뉴 평가하기",
    rightButtonClicked : () -> Unit = {},
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        OutlinedButton(
            onClick = leftButtonClicked,
            shape = RoundedCornerShape(23.dp),
            modifier = Modifier
                .width(126.dp)
                .height(mainButtonHeightValue.dp),
            border = BorderStroke(1.dp, MainColor)
        ) {
            Text(
                text = leftButtonText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MainColor
            )
        }

        TextButton(
            shape = RoundedCornerShape(mainButtonRoundValue.dp),
            onClick = rightButtonClicked,
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainColor,
            ),
            modifier = Modifier
                .width(178.dp)
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