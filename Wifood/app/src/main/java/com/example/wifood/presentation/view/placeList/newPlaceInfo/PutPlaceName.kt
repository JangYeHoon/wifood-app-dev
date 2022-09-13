package com.example.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.DialogCenterDivider
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*

@Composable
fun PutPlaceName(

){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = sidePaddingValue.dp)
    ){
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.weight(1f))
        YOGOLargeText(
            text = "맛집 이름을\n입력해주세요."
        )
        Spacer(Modifier.height(24.dp))
        PutPlaceNameTextField()
        Spacer(Modifier.weight(1f))
        MainButton(
            text = "다음",
            onClick = { /*TODO*/ },
            activate = false
        )
        Spacer(Modifier.height(buttonBottomValue.dp))


    }
}

@Composable
fun PutPlaceNameTextField(
    text:String = "",
    placeholder:String = "맛집이름",
    onValueChange:(String) -> Unit = {}
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
                text = placeholder,
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
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                if (text.isNotEmpty()){
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(10.dp))
                }
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "left button of top app bar",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        }
    )
}