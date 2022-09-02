package com.example.wifood.presentation.view.placeList.newPlaceInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyPlaceGroupView(
){
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = sidePaddingValue.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "맛집 그룹을 수정하세요",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(54.dp))
                Text(
                    text = "그룹명",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(8.dp))
                YOGOBaseTextField(
                    text = "",
                    onValueChange = {},
                    placeholderText = "샤로수길 맛집 저장소"
                )
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "상세 설명",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(8.dp))
                YOGOBaseTextField(
                    text = "",
                    onValueChange = {},
                    placeholderText = "디저트, 맛집, 술집"
                )
                Spacer(Modifier.weight(1f))
                MainButton(
                    text = "맛집 수정하기",
                    onClick = { /*TODO*/ }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}

@Composable
fun YOGOBaseTextField(
    text:String,
    onValueChange:(String)->Unit,
    placeholderText:String
){
    TextField(
        value = text,
        onValueChange = onValueChange,
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
        )
    )
}

