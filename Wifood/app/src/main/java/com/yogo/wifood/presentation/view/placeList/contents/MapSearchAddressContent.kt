package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*


@Composable
fun MapSearchAddressContent(
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SpaceWithTextTop("맛집 주소 등록")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // map component
            MapContent()

            // other components
            ///*
            Column(
                modifier = Modifier
                    .background(Color.Unspecified)
                    .padding(
                        horizontal = sidePaddingValue.dp,
                        vertical = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpaceWithTextBlack()
                Spacer(Modifier.weight(1f))
                MapIcon()
                Spacer(Modifier.weight(1f))
                PointLocationAddress()
            }
        }
        SpaceWithTextBottom("이 위치로 맛집 주소 등록하기")
    }
}

@Composable
fun SpaceWithTextTop(
    text: String = "맛집 주소 등록",
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YOGOPR16_title(text)
    }
}


@Composable
fun MapContent(

) {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun MapIcon(
) {
    Icon(
        ImageVector.vectorResource(id = R.drawable.ic_place_location_icon),
        contentDescription = "left button of top app bar",
        modifier = Modifier.wrapContentSize(),
        tint = Color.Unspecified
    )
}


@Composable
fun SpaceWithTextBlack(
    text: String = "지도를 움직여 주소 위치를 설정해주세요",
    backgroundColor: Color = Color(0xCC000000),
) {
    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YOGOPR12_formInfo(
            text = "지도를 움직여 주소 위치를 설정해주세요.",
            color = Color.White
        )
    }
}

@Composable
fun PointLocationAddress(
    coarseAddress: String = "서울특별시 관악구 봉천동 466-56",
    fineAddress: String = "서울특별시 관악구 은천로25길 4-5 (채움빌-아트)",
    backgroundColor: Color = Color(0xF2FFFFFF),
) {
    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = coarseAddress,
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Black2Color,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "[도로명] $fineAddress",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            color = Gray03Color,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SpaceWithTextBottom(
    text: String = "이 위치로 맛집 주소 등록하기",
    onClick: () -> Unit = {}
) {
    val interactionSource = MutableInteractionSource()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                color = MainColor
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
            .padding(top = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontFamily = mainFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}