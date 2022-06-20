package com.example.wifood.presentation.view.login.join

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.fontRoboto
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.buttonBottomValue

@ExperimentalComposeUiApi
@Composable
fun GetUserFavorView(
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 85.dp),
            shape = RoundedCornerShape(30.dp),
            color = Color(0xFFFFFFFF)
        ){
            GetUserFavorContent()
        }
    }
}

@Composable
fun GetUserFavorContent(){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .padding(top = 85.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ){
        Text(
            text = "조금만 더\n알려주세요!",
            fontFamily = mainFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "등록한 정보는 추후 개인화 추천에 이용됩니다",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color(0xFF565656)
        )
        Spacer(Modifier.height(40.dp))
        UserFavorRadioGroup()
        Text(
            text = "좋아하면 선택해주세요",
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(5.dp))
        UserFavorButtonGroup()
        Spacer(Modifier.height(50.dp))
        MainButton(
            text = "등록하기",
            onClick = {}
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}

@Composable
fun UserFavorRadioGroup(){
    val favorSpacerValue = 25
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        YOGORadioGroup(titleText = "매운맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "단맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "짠맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "신맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "쓴맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
    }
}

@Composable
fun YOGORadioGroup(
    titleText:String
){
    //semibold 13
    Column(){
        Text(
            text = titleText,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.Black
        )
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                YOGORadioButton(false)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "매우 싫음",
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontRoboto,
                    fontSize = 10.sp,
                    color = Gray01Color
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                YOGORadioButton(false)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "싫음",
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontRoboto,
                    fontSize = 10.sp,
                    color = Gray01Color
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                YOGORadioButton(false)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "보통",
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontRoboto,
                    fontSize = 10.sp,
                    color = Gray01Color
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                YOGORadioButton(false)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "좋음",
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontRoboto,
                    fontSize = 10.sp,
                    color = Gray01Color
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                YOGORadioButton(false)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "매우 좋음",
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontRoboto,
                    fontSize = 10.sp,
                    color = Gray01Color
                )
            }
        }
    }
}

@Composable
fun YOGORadioButton(
    buttonClicked:Boolean = false
){
    IconButton(
        onClick = {},
        modifier = Modifier
            .wrapContentSize()
    ){
        Icon(
            ImageVector.vectorResource(id = if (buttonClicked) R.drawable.ic_selected_radiobutton else R.drawable.ic_unselected_radiobutton),
            contentDescription = "",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun UserFavorButtonGroup(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Column(){

        }
    }
}