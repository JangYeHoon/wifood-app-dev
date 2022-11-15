package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@Composable
fun PutPlaceAddressList(

) {
    val scrollState = rememberScrollState()
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

        // Content
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            for (i in 0..3) {
                PointLocationAddress2(
                    isClicked = true
                )
                Spacer(Modifier.height(16.dp))
            }
        }

        Spacer(Modifier.weight(1f))
        MainButton(
            text = "다음",
            onClick = { /*TODO*/ },
            activate = false
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PointLocationAddress2(
    buildingAddress: String = "서울특별시 관악구 봉천동 466-51",
    roadAddress: String = "서울특별시 관악구 은천로25가길 10 (네오주택)",
    isClicked: Boolean = false,
    function: () -> Unit = {}
) {
    val shape = RoundedCornerShape(10.dp)
    val interactionSource = MutableInteractionSource()
    Column(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                function()
            }
            .background(
                color = Color.White,
                shape = shape
            )
            .fillMaxWidth()
            .border(
                width = if (isClicked) 1.dp else 0.dp,
                color = if (isClicked) MainColor else Color.Unspecified,
                shape = shape
            )
            .shadow(
                elevation = 1.dp,
                shape = shape,
                ambientColor = Color(0x0D000000)
            )
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            )
    ) {
        Text(
            text = buildAnnotatedString {
                append("지번명 : ")
                withStyle(
                    SpanStyle(
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Black2Color
                    )
                ) {
                    append(buildingAddress)
                }
            },
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black2Color
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "도로명 : $roadAddress",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black2Color
        )
    }
}