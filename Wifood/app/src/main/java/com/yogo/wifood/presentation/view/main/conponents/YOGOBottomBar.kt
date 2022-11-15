package com.yogo.wifood.presentation.view.main.conponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.YOGOPR12_formInfo
import com.yogo.wifood.view.ui.theme.MainColor
import com.yogo.wifood.view.ui.theme.bottomBarHeight

@Preview(showBackground = true)
@Composable
fun YOGOBottomBar(
    selected: String = "map",
    pushMapClicked: () -> Unit = {},
    pushListClicked: () -> Unit = {},
    pushSettingClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .fillMaxWidth()
            .height(bottomBarHeight.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .padding(
                horizontal = 46.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarItems(
            itemId = R.drawable.ic_bottom_map_icon,
            itemPushedId = R.drawable.ic_bottom_map_clicked_icon,
            itemText = "지도",
            pushed = selected == "map",
            onClicked = pushMapClicked
        )
        BottomBarItems(
            itemId = R.drawable.ic_bottom_list_icon,
            itemPushedId = R.drawable.ic_bottom_list_clicked_icon,
            itemText = "리스트",
            pushed = selected == "list",
            onClicked = pushListClicked
        )
        BottomBarItems(
            itemId = R.drawable.ic_bottom_settings_icon,
            itemPushedId = R.drawable.ic_bottom_setting_clicked_icon,
            itemText = "설정",
            pushed = selected == "mypage",
            onClicked = pushSettingClicked
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun BottomBarItems(
    itemId: Int = R.drawable.ic_bottom_map_icon,
    itemPushedId: Int = R.drawable.ic_bottom_map_clicked_icon,
    itemText: String = "지도",
    pushed: Boolean = false,
    onClicked: () -> Unit = {}
) {

    val interactionSource = MutableInteractionSource()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .height(bottomBarHeight.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClicked()
            }
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            ImageVector.vectorResource(if (pushed) itemPushedId else itemId),
            contentDescription = "App bottom Items",
            modifier = Modifier
                .wrapContentSize(),
            tint = Color.Unspecified
        )
        Spacer(Modifier.height(5.dp))
        YOGOPR12_formInfo(
            text = itemText,
            color = if (pushed) MainColor else Color(0xBD000000),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
    }

}
