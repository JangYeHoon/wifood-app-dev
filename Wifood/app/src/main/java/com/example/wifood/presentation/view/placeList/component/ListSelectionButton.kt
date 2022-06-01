package com.example.wifood.presentation.view.placeList.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun ListSelectionButton(
    buttonText:String = "그룹 선택",
    onClick:()->Unit = {}
){
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),

    )
    {

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        MainColor
                    ),
                ){
                    append("*")
                }
                append(" ")
                append(buttonText)
            },
            color = Gray01Color,
            fontSize = 15.sp,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        Icon(
            ImageVector.vectorResource(R.drawable.ic_right_arrow),
            contentDescription = "back button",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )

    }
}