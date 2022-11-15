package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.login.util.numberCommaFilter
import com.yogo.wifood.presentation.view.placeList.component.YOGOBaseTextField
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*
import kotlinx.coroutines.selects.select

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyPlaceGroupContent(
) {
    val scrollState = rememberScrollState()

    Column {
        MyPageTopAppBar(
            titleText = "",
            leftButtonClicked = {

            },
            showUnderLine = false
        )
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