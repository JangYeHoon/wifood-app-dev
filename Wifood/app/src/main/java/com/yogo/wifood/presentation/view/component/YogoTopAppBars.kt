package com.yogo.wifood.presentation.view.component

import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun MyPageTopAppBar(
    titleText:String = "프로필 수정",
    leftButtonOn:Boolean = true,
    leftButtonClicked:() -> Unit = {},
    rightButtonOn:Boolean = false,
    rightButtonText:String = "완료",
    rightButtonClicked: () -> Unit = {},
    showUnderLine:Boolean = true
){
    val height:Int = 48
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        ){
            if (leftButtonOn){
                IconButton(
                    onClick = leftButtonClicked,
                    modifier = Modifier
                        .height(height.dp)
                        .width(40.dp)
                        .align(Alignment.CenterStart)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ){
                            leftButtonClicked()
                        }
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
            Text(
                text = titleText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
            if (rightButtonOn){
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .height(height.dp)
                        .wrapContentWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = rightButtonText,
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MainColor,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(
                                end = 20.dp
                            )
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ){
                                rightButtonClicked()
                            },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Divider(
            thickness = 1.dp,
            color = if (showUnderLine) Color(0xFFF1F1F1) else Color.White
        )
    }
}

@Composable
fun DoubleButtonTopAppBar(
    titleText:String = "맛집 리스트",
    leftButtonOn:Boolean = true,
    leftButtonClicked:() -> Unit = {},
    rightButtonOn:Boolean = true,
    rightButtonClicked:() -> Unit = {},
    showUnderLine:Boolean = true
){
    val height:Int = 48
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        ){
            if (leftButtonOn){
                IconButton(
                    onClick = leftButtonClicked,
                    modifier = Modifier
                        .height(height.dp)
                        .width(40.dp)
                        .align(Alignment.CenterStart)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ){
                            leftButtonClicked()
                        }
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
            Text(
                text = titleText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )

            if(rightButtonOn){
                IconButton(
                    onClick = rightButtonClicked,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ){
                            rightButtonClicked()
                        }
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_add_list_button),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }

        }
        Divider(
            thickness = 1.dp,
            color = if (showUnderLine) Color(0xFFF1F1F1) else Color.White
        )
    }
}

@Composable
fun PlaceInputTopAppBar(
    leftButtonClicked:() -> Unit = {},
    rightButtonClicked:() -> Unit = {},
    leftButtonOn: Boolean = true,
    rightButtonOn: Boolean = false,
    rightButtonText: String = "등록하기"
){
    val interactionSource = remember {
        MutableInteractionSource()
    }

    TopAppBar(
        title = {
        },
        navigationIcon = {
            if (leftButtonOn){
                IconButton(
                    onClick = leftButtonClicked,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
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
                Text(
                    text = rightButtonText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MainColor,
                    modifier = Modifier
                        .clickable (
                            indication = null,
                            interactionSource = interactionSource
                        ){
                            rightButtonClicked()
                        }
                        .padding(end = 20.dp)
                )
            }
        },
        contentColor = Color(0xFFF1F1F1),
        elevation = 1.dp
    )
}