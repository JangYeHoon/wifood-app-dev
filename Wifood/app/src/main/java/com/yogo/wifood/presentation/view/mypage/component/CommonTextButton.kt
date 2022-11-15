package com.yogo.wifood.presentation.view.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.MainColor

@Composable
fun CommonTextButton(
    text: String = "내 정보 수정",
    withButton: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
            .padding(
                horizontal = 24.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(Modifier.weight(1f))
        if (withButton) {
            Icon(
                ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = "back button",
                modifier = Modifier
                    .wrapContentSize()
                    //.width(5.dp)
                    //.height(9.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Unspecified
            )
        }
    }

}

@Composable
fun versionInfoField(
    text: String,
    onClick: () -> Unit = {},
    version: String = "v1.0"
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
            .padding(
                horizontal = 24.dp,
                vertical = 14.dp
            )
    ) {
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = version,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = MainColor
        )
    }
}