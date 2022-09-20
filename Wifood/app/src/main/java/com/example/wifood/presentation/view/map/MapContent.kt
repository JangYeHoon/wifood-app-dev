package com.example.wifood.presentation.view.map

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.*
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun MapContent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                //color = Color(0xFFEDEDD9)
                color = Color(0xFF000000)
            )
    ) {
        MapSearchTextField()
        SelectPlaceGroupContent()

        // google map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
            ){
                Spacer(Modifier.weight(1f))
                CurrentLocationIcon()
                Spacer(Modifier.weight(1f))
            }
        }
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
fun MapSearchTextField(
    searchText: String = "서울역",
    onFindLocationClicked: () -> Unit = {},
    onSearchLocationClicked: ()-> Unit = {},
    onValueChanged:(String) ->  Unit = {},
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextField(
        value = searchText,
        onValueChange = onValueChanged,
        leadingIcon = {
            IconButton(
                onClick = onFindLocationClicked,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ){
                        onFindLocationClicked()
                    }
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_map_place_location_icon),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = EnableColor,
            unfocusedIndicatorColor = EnableColor,
            textColor = Black2Color,
            placeholderColor = EnableColor
        ),
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Gray01Color
        ),
        placeholder = {
            Text(
                text = "찾는 이름을 입력해줒세요~",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = EnableColor
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onSearchLocationClicked,
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_search_icon),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    )
}

@Preview(showBackground = true)
@Composable
fun SelectPlaceGroupContent(

) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .horizontalScroll(scrollState)
            .background(
                color = Color(0xE6FFFFFF)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            )
    ){
        MainButtonToggle(
            text = "전체"
        )
        Spacer(Modifier.width(12.dp))
        MainButtonToggle(
            text = "위시리스트"
        )
        Spacer(Modifier.width(12.dp))
        MainButtonToggle(
            text = "회사근처맛집"
        )
        Spacer(Modifier.width(12.dp))
        MainButtonToggle(
            text = "테이크아웃"
        )
        Spacer(Modifier.width(12.dp))
        MainButtonToggle(
            text = "전체"
        )
        Spacer(Modifier.width(12.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun CurrentLocationUnion(
    titleText: String = "파리바게트",
    explainText: String = "황홀한맛입니다아아",
    rating: Int = 3,
    onClicked: () -> Unit = {}
) {
    val InteractionSource = MutableInteractionSource()
    var unionCount = 0
    if (explainText.isNotEmpty())
        unionCount += 1
    if (rating != 0)
        unionCount += 1

    if (unionCount == 0)
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
    ) {
        Icon(
            ImageVector.vectorResource(unionCount),
            contentDescription = "App bottom Items",
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier
                .padding(
                    top = (8.5).dp,
                    bottom = (13.47).dp
                )
                .padding(horizontal = 14.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (rating > 0) {
                Row(

                ) {
                    for (i in 0..4) {
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
            if (explainText.isNotEmpty()) {
                Text(
                    text = explainText.substring(0, 6) + "...",
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
) {
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
    placesNumber: Int = 5,
    modifier: Modifier = Modifier
) {
    var iconColor = 0
    if (placeColor.equals("57BF7C")) {
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

    Box(

    ){
        Icon(
            ImageVector.vectorResource(iconColor),
            contentDescription = "Map food location icon",
            modifier = Modifier
                .wrapContentSize(),
            tint = Color.Unspecified
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(
                    if (placesNumber < 10) 18.dp else 25.dp
                )
                .background(
                    color = Black2Color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = placesNumber.toString(),
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = (12.86).sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview(showBackground = true)
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
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
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

@Preview(showBackground = true)
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