package com.yogo.wifood.presentation.view.placeList.componentGroup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.data.remote.dto.MenuGradeDto
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color

@Composable
fun PlaceInfoShowMenu(
    menuName: String = "W코스",
    menuPrice: Int = 19800,
    menuMemo: String = "가격값하는 맛, 고기는 말할 것 없고 랍스타 맛있음"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = menuName,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF5B5B5B)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = MenuGradeDto().getPriceToCommaString(menuPrice),
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF5B5B5B)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = menuMemo,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Gray01Color
        )
    }
}