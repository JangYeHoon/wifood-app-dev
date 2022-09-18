package com.example.wifood.presentation.view.map

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.RatingStarIcon
import com.example.wifood.presentation.view.component.SingleRatingStar
import com.example.wifood.presentation.view.component.YOGOPR12_formInfo
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun MapContent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MapSearchTextField()
        SelectPlaceGroupContent()
        GoogleMap()
        AppBottomContent(
            pushMap = true,
            pushMapClicked = {},
            pushList = false,
            pushListClicked = {},
            pushSetting = false,
            pushSettingClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapSearchTextField() {

}

@Preview(showBackground = true)
@Composable
fun SelectPlaceGroupContent(

) {

}

@Preview(showBackground = true)
@Composable
fun GoogleMap(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ){
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            CurrentLocationIcon(Modifier.align(Alignment.Center))
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentLocationUnion(
    titleText: String = "파리바게트",
    explainText: String = "",
    rating: Int = 0,
    onClicked: () -> Unit = {}
){
    val InteractionSource = MutableInteractionSource()
    var unionCount = 0
    if (explainText.isNotEmpty())
        unionCount += 1
    if (rating != 0)
        unionCount += 1

    if (unionCount == 0 )
        unionCount = R.drawable.ic_map_location_info_with_title
    else if (unionCount == 1)
        unionCount = R.drawable.ic_map_location_info_union_with_title_explain
    else if (unionCount == 2)
        unionCount = R.drawable.ic_map_location_info_union_with_title_explain_rating

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clickable(
                indication = null,
                interactionSource = InteractionSource
            ) {
                onClicked()
            }
            .background(
                color = Color.Unspecified
            )
    ){
        Icon(
            ImageVector.vectorResource(unionCount),
            contentDescription = "App bottom Items",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier
                .width(106.dp)
                .padding(
                    top = (8.5).dp,
                    bottom = (13.47).dp
                )
                .padding(horizontal = 14.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            if(rating > 0){
                Row(

                ){
                    for (i in 0..4){
                        SingleRatingStar(
                            isClicked = i < rating,
                            onClick = {},
                            starSize = 8
                        )
                        Spacer(Modifier.width(1.dp))
                    }
                }
                Spacer(Modifier.height(3.dp))
            }
            Text(
                text = titleText,
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Black2Color,
                textAlign = TextAlign.Center
            )
            if (explainText.isNotEmpty()){
                Text(
                    text = explainText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Gray01Color,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CurrentLocationIcon(
    modifier: Modifier = Modifier
){
    Icon(
        ImageVector.vectorResource(R.drawable.ic_map_current_location_icon),
        contentDescription = "App bottom Items",
        modifier = Modifier
            .wrapContentSize(),
        tint = Color.Unspecified
    )
}

@Preview(showBackground = true)
@Composable
fun UserPlaceLocationIcon(
    groupCount: Int = 4,
    placeColor: String = "57BF7C",
    modifier: Modifier = Modifier
){
    var iconColor = 0
    if (placeColor.equals("57BF7C")){
        iconColor = R.drawable.ic_map_place_icon_57bf7c
    } else if (placeColor.equals("59C3B5")) {
        iconColor = R.drawable.ic_map_place_icon_59c3b5
    } else if (placeColor.equals("6153A7")) {
        iconColor = R.drawable.ic_map_place_icon_6153a7
    } else if (placeColor.equals("7EB2FF")) {
        iconColor = R.drawable.ic_map_place_icon_7eb2ff
    } else if (placeColor.equals("8DB353")) {
        iconColor = R.drawable.ic_map_place_icon_8bd353
    } else if (placeColor.equals("A57DBA")) {
        iconColor = R.drawable.ic_map_place_icon_a57dba
    } else if (placeColor.equals("AAA333")) {
        iconColor = R.drawable.ic_map_place_icon_aaa333
    } else if (placeColor.equals("FE6080")) {
        iconColor = R.drawable.ic_map_place_icon_fe6080
    } else if (placeColor.equals("FF8A00")) {
        iconColor = R.drawable.ic_map_place_icon_ff8a00
    } else if (placeColor.equals("FFC08D")) {
        iconColor = R.drawable.ic_map_place_icon_ffc08d
    }

    Icon(
        ImageVector.vectorResource(iconColor),
        contentDescription = "Map food location icon",
        modifier = Modifier
            .wrapContentSize(),
        tint = Color.Unspecified
    )
}

@Composable
fun AppBottomContent(
    pushMap: Boolean = true,
    pushMapClicked: () -> Unit = {},
    pushList: Boolean = false,
    pushListClicked: () -> Unit = {},
    pushSetting: Boolean = false,
    pushSettingClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
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
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppBottomItems(
            itemId = R.drawable.ic_bottom_map_icon,
            itemText = "지도",
            pushed = pushMap,
            onClicked = pushMapClicked
        )
        AppBottomItems(
            itemId = R.drawable.ic_bottom_list_icon,
            itemText = "리스트",
            pushed = pushList,
            onClicked = pushListClicked
        )
        AppBottomItems(
            itemId = R.drawable.ic_bottom_settings_icon,
            itemText = "설정",
            pushed = pushSetting,
            onClicked = pushSettingClicked
        )
    }
}

@Composable
fun AppBottomItems(
    itemId: Int = R.drawable.ic_bottom_map_icon,
    itemText: String = "지도",
    pushed: Boolean = false,
    onClicked: () -> Unit = {}
) {

    val interactionSource = MutableInteractionSource()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClicked()
            }
    ) {
        Icon(
            ImageVector.vectorResource(itemId),
            contentDescription = "App bottom Items",
            modifier = Modifier
                .wrapContentSize(),
            tint = if (pushed) MainColor else Color.Unspecified
        )
        Spacer(Modifier.height(5.dp))
        YOGOPR12_formInfo(
            text = itemText,
            color = if (pushed) MainColor else Color(0xBD000000),
            textAlign = TextAlign.Center
        )
    }
}