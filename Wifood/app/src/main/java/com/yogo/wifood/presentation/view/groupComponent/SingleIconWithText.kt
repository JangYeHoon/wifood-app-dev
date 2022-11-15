package com.yogo.wifood.presentation.view.groupComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color


@Composable
fun SingleIconWithText(
    text: String = "오이",
    UnClickedSourceId: Int = R.drawable.ic_favor_cucumber,
    ClickedSourceId: Int = R.drawable.ic_favor_cucumber_clicked,
    isClicked: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
        ) {
            Icon(
                ImageVector.vectorResource(id = if (isClicked) ClickedSourceId else UnClickedSourceId),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Gray01Color
        )
    }
}


// TODO GetUserFavorView 수정 후 삭제
@Composable
fun SingleIconWithText(
    text: String,
    UnClickedSourceId: Int = R.drawable.ic_favor_cucumber,
    ClickedSourceId: Int = R.drawable.ic_favor_cucumber_clicked,
    isClicked: MutableState<Boolean> = mutableStateOf(false),
    onClick: (Boolean) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {

            },
            modifier = Modifier
                .size(56.dp)
        ) {
            Icon(
                ImageVector.vectorResource(id = if (isClicked.value) ClickedSourceId else UnClickedSourceId),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        isClicked.value = !isClicked.value
                        onClick(isClicked.value)
                    },
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Gray01Color
        )
    }
}