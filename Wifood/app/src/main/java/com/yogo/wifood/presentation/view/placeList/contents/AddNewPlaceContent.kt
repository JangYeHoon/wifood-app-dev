package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.view.ui.theme.Gray01Color

@Composable
fun AddNewPlaceContent(
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_empty_circle),
                contentDescription = "left button of top app bar",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(39.dp))
            YOGOLargeText(
                text = "맛집 등록이\n완료되었습니다.",
                color = Gray01Color,
                textAlign = TextAlign.Center
            )
        }
    }
}