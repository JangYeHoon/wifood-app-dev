package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.bottomSheetButtonColor

@Composable
fun BottomSheetListItem(
    icon: ImageVector,
    title: String = "취소",
    onItemClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(mainButtonHeightValue.dp)
            .background(color = bottomSheetButtonColor)
            .padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = "Icon")
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.White)
    }
}

//@Preview(showBackground = true)
@Composable
fun BottomSheetYOGOButton(
    title: String = "취소",
    onItemClick: (String) -> Unit = {},
    topStart: Int = 12,
    topEnd: Int = 12,
    bottomStart: Int = 12,
    bottomEnd: Int = 12,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onItemClick(title)
                }
            )
            .height(46.dp)
            .background(
                color = bottomSheetButtonColor,
                shape = RoundedCornerShape(
                    topStart = topStart.dp,
                    topEnd = topEnd.dp,
                    bottomStart = bottomStart.dp,
                    bottomEnd = bottomEnd.dp
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = title,
            color = Color.White,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        Spacer(Modifier.weight(1f))
    }
}