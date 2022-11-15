package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.yogo.wifood.presentation.view.component.DialogCenterDivider
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.presentation.view.component.YOGOTextPM15
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*


@Composable
fun PutPlaceAddress(

) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = sidePaddingValue.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.weight(1f))
        YOGOLargeText(
            text = "맛집 주소를\n입력해주세요."
        )
        Spacer(Modifier.height(24.dp))
        PutPlaceAddressTextField()
        Spacer(Modifier.height(16.dp))
        FindAddressOnMapButton()
        Spacer(Modifier.weight(1f))
    }
}


@Composable
fun PutPlaceAddressTextField(
    text: String = "은천로25길길 4-5",
    placeholder: String = "맛집주소",
    onValueChange: (String) -> Unit = {},
    onSearchBtnClick: () -> Unit = {},
    onDeleteBtnClick: () -> Unit = {}
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = Gray01Color
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = EnableColor
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = MainColor,
            textColor = Gray01Color,
            placeholderColor = EnableColor,
            focusedIndicatorColor = MainColor,
            unfocusedIndicatorColor = EnableColor
        ),
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (text.isNotEmpty()) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                onDeleteBtnClick()
                            },
                        tint = Color.Unspecified,
                    )
                    Spacer(Modifier.width(10.dp))
                }
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_search_icon),
                    contentDescription = "left button of top app bar",
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            onSearchBtnClick()
                        },
                    tint = Color.Unspecified
                )
            }
        }
    )
}

@Composable
fun FindAddressOnMapButton(
    text: String = "지도에서 주소찾기",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = MutableInteractionSource()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            ImageVector.vectorResource(id = R.drawable.ic_search_from_map),
            contentDescription = "left button of top app bar",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )
        Spacer(Modifier.width(6.dp))
        YOGOTextPM15(
            text = text
        )
        Spacer(Modifier.weight(1f))
    }
}