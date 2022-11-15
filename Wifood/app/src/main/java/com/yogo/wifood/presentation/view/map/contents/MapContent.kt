package com.yogo.wifood.presentation.view.map.contents

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButtonToggle
import com.yogo.wifood.presentation.view.component.SingleRatingStar
import com.yogo.wifood.presentation.view.main.conponents.YOGOBottomBar
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.Gray01Color

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun MapContent(
    onSearchPlaceIconClicked: () -> Unit = {},
    searchText: String = "",
    onSearchTextValueChanged: (String) -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
    placeList: List<String> = listOf("회사근처맛집", "테이크아웃"),
    bottomBarSelected: String = "map",
    bottomMapClicked: () -> Unit = {},
    bottomListClicked: () -> Unit = {},
    bottomSettingClicked: () -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            YOGOBottomBar(
                selected = bottomBarSelected,
                pushMapClicked = bottomMapClicked,
                pushListClicked = bottomListClicked,
                pushSettingClicked = bottomSettingClicked
            )
        },
        floatingActionButton = {
            YOGOFloatingActionGroup(

            )
        }
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
                ) {
                    Spacer(Modifier.weight(1f))
                    CurrentLocationIcon()
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun MapSearchTextField(
    searchText: String = "서울역",
    onFindLocationClicked: () -> Unit = {},
    onSearchLocationClicked: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextField(
        value = searchText,
        onValueChange = onValueChanged,
        enabled = false,
        leadingIcon = {
            IconButton(
                onClick = onFindLocationClicked,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
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
            .wrapContentHeight()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onSearchLocationClicked()
            },
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
                text = "찾는 이름을 입력해주세요~",
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

//@Preview(showBackground = true)
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
                vertical = 5.dp
            )
    ) {
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
    titleText: String = "파리바게트가 뭔지 몰라요",
    explainText: String = "황홀한맛입니다아아 매우 황홀해요~~~",
    rating: Int = 2,
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
                            starSize = 9
                        )
                        Spacer(Modifier.width(1.dp))
                    }
                }
                Spacer(Modifier.height(3.dp))
            }
            Text(
                text = if (titleText.length >= 5) titleText.substring(0, 5) + "..." else titleText,
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                color = Black2Color,
                textAlign = TextAlign.Center
            )
            if (explainText.isNotEmpty()) {
                Text(
                    text =
                    if (explainText.length >= 5)
                        explainText.substring(0, 5) + "..."
                    else explainText,
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

//@Preview(showBackground = true)
@Composable
fun CurrentLocationIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        ImageVector.vectorResource(R.drawable.ic_current_user_location_icon),
        contentDescription = "App bottom Items",
        modifier = Modifier
            .wrapContentSize(),
        tint = Color.Unspecified
    )
}

//@Preview(showBackground = true)
@Composable
fun UserPlaceLocationIcon(
    groupCount: Int = 4,
    placeColor: String = "57BF7C",
    placesNumber: Int = 5,
    modifier: Modifier = Modifier
) {
    var iconColor = 0
//    if (placeColor.equals("57BF7C")) {
//        iconColor = R.drawable.ic_map_place_icon_57bf7c
//    } else if (placeColor.equals("59C3B5")) {
//        iconColor = R.drawable.ic_map_place_icon_59c3b5
//    } else if (placeColor.equals("6153A7")) {
//        iconColor = R.drawable.ic_map_place_icon_6153a7
//    } else if (placeColor.equals("7EB2FF")) {
//        iconColor = R.drawable.ic_map_place_icon_7eb2ff
//    } else if (placeColor.equals("8DB353")) {
//        iconColor = R.drawable.ic_map_place_icon_8bd353
//    } else if (placeColor.equals("A57DBA")) {
//        iconColor = R.drawable.ic_map_place_icon_a57dba
//    } else if (placeColor.equals("AAA333")) {
//        iconColor = R.drawable.ic_map_place_icon_aaa333
//    } else if (placeColor.equals("FE6080")) {
//        iconColor = R.drawable.ic_map_place_icon_fe6080
//    } else if (placeColor.equals("FF8A00")) {
//        iconColor = R.drawable.ic_map_place_icon_ff8a00
//    } else if (placeColor.equals("FFC08D")) {
//        iconColor = R.drawable.ic_map_place_icon_ffc08d
//    }

    Box(

    ) {
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
        ) {
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
fun YOGOFloatingActionGroup(
    onCurrentFloatingButtonClicked: () -> Unit = {},
    onAddLocationFloatingButtonClicked: () -> Unit = {}
) {
    val interactionSource = MutableInteractionSource()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color.Transparent
            )
    ) {
        FloatingActionButton(
            onClick = onCurrentFloatingButtonClicked,
            modifier = Modifier
                .wrapContentSize(),
            elevation = FloatingActionButtonDefaults.elevation(16.dp),
            backgroundColor = Color.White
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_floating_button_current_location),
                contentDescription = "current location floating button",
                modifier = Modifier
                    .size(54.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.height(9.dp))
        FloatingActionButton(
            onClick = onAddLocationFloatingButtonClicked,
            modifier = Modifier
                .size(70.dp),
            elevation = FloatingActionButtonDefaults.elevation(16.dp)
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_floating_button_add_location),
                contentDescription = "",
                modifier = Modifier
                    .size(70.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.height(75.dp))
    }
}