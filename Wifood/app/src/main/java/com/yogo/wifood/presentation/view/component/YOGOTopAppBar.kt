package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.R

@Composable
fun YOGOTopAppBar(
    text:String = "내 맛집",
    leftButtonOn:Boolean = true,
    leftButtonClicked:() -> Unit = {},
    leftButtonSource:Int = R.drawable.ic_back_arrow,
    rightButtonOn:Boolean = false,
    rightButtonSource:Int = R.drawable.ic_plus_button_wide,
    rightButtonClicked:() -> Unit = {}

){

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.Center
            ) {
                Text(
                    text = text,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            if (leftButtonOn){

                IconButton(
                    onClick = leftButtonClicked,
                    modifier = Modifier.wrapContentSize()
                )
                {
                    Icon(
                        ImageVector.vectorResource(id = leftButtonSource),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
        },
        backgroundColor = Color.White,
        actions = {
            if (rightButtonOn){
                IconButton(
                    onClick = rightButtonClicked,
                    modifier = Modifier.wrapContentSize()
                )
                {
                    Icon(
                        ImageVector.vectorResource(id = rightButtonSource),
                        contentDescription = "right button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}