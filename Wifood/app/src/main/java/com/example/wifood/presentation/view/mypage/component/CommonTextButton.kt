package com.example.wifood.presentation.view.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.ui.theme.mainFont

@Composable
fun CommonTextButton(
    text:String = "내 정보 수정",
    withButton:Boolean = false,
    onClick: () -> Unit = {}
    ){
    Column(
        modifier = Modifier
            .clickable{
                onClick()
            }
            .padding(horizontal = 24.dp)
            .padding(vertical = 15.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = Color.Black
            )
            Spacer(Modifier.weight(1f))
            if (withButton){
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_right_arrow),
                    contentDescription = "back button",
                    modifier = Modifier
                        .width(5.dp)
                        .height(9.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun CommonTextButtonSB(
    text:AnnotatedString = buildAnnotatedString {},
    withButton:Boolean = false,
    onClick: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .clickable{
                onClick()
            }
            .padding(horizontal = 24.dp)
            .padding(vertical = 15.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = Color.Black
            )
            Spacer(Modifier.weight(1f))
            if (withButton){
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_right_arrow),
                    contentDescription = "back button",
                    modifier = Modifier
                        .width(5.dp)
                        .height(9.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Unspecified
                )
            }
        }
    }
}