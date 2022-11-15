package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.login.component.YOGORadioButton
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.Gray03Color
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue

//@Preview(showBackground = true)
@Composable
fun GetUserGenderContent(
    onGenderClicked: () -> Unit = {},
    gender:Boolean = false,
    onNextButtonClicked: () -> Unit = {}
){
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ) {
            Spacer(Modifier.height(106.dp))
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_3by4),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "성별을 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "회원님께 더 선호할만한 맛집을 추천할 수 있도록\n성별을 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Gray03Color
            )
            Spacer(Modifier.height(76.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                YOGORadioButton(
                    text = "남성",
                    onClick = onGenderClicked,
                    selected = gender
                )
                YOGORadioButton(
                    onClick = onGenderClicked,
                    selected = !gender
                )
            }
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = onNextButtonClicked
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}