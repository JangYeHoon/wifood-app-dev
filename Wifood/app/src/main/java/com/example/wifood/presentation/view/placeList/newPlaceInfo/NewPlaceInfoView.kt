package com.example.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.data.remote.dto.MenuGradeDto
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.*
import com.example.wifood.presentation.view.login.component.SnsIconButton
import com.example.wifood.presentation.view.placeList.RatedMode
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewPlaceInfoView(

){
    val scrollState = rememberScrollState()
    val interactionSource = MutableInteractionSource()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        // Top Left Right Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            VectorIconWithNoInteraction(
                resource = R.drawable.ic_place_info_back_button,
                onClick = {
                    //navController.popBackStack()
                }
            )
            VectorIconWithNoInteraction(
                resource = R.drawable.ic_place_info_option_button,
                onClick = {
                    /*
                    scope.launch {
                        modalBottomSheetState.show()
                    }*/
                }
            )
        }

        // Main Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            // Top Image
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            )

            // Place abstract and location view
            PlaceInfoAbstractView(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .offset(y = (-30).dp)
            )

            // Show Location
            Row(
                modifier = Modifier
                    .clickable {
                        //navController.navigate("${Route.Main.route}?placeLat=${state.place!!.latitude}&placeLng=${state.place.longitude}")
                    }
                    .padding(horizontal = sidePaddingValue.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_group_pin),
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "서울시 용산구 효창동 522-2 1F",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Gray01Color
                )
            }
            Spacer(Modifier.height(10.dp))
            //GoogleMap
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = sidePaddingValue.dp)
                    .background(color = Color.Black)
            ){
                Spacer(Modifier.height(82.dp))
            }
            Spacer(Modifier.height(18.dp))
            // Divider if review or something exists
            CustomDivider(
                fillColor = Color(0xFFF4F4F4),
                borderColor = Color(0xFFE7E7E7),
                thickness = 4
            )
            // Menu if exists
            val isMenuExists = true
            val isReviewExists = true
            // Reviews if it exists
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = sidePaddingValue.dp,
                        vertical = 22.dp
                    )
            ){
                if (isReviewExists){
                    YOGOTextPM15(
                        text = "맛집 리뷰"
                    )
                    Spacer(Modifier.height(10.dp))
                    ReviewTextField(
                        text = "3시에서 4시 해피아워 10% 할인됨, 미리 예약하기, 본점이랑 비교하기 미션",
                        onValueChange = {},
                        placeholder = "",
                        modifier = Modifier
                            .wrapContentHeight(),
                        showCount = false,
                        fontSize = 12
                    )
                    Spacer(Modifier.height(10.dp))
                    //                ShowPhotoList(
//                    listOf(
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                    )
//                )
                    Spacer(Modifier.height(28.dp))
                }

                if (isMenuExists){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color(0xFFF1F1F1),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 22.dp
                            )
                    ){
                        PlaceInfoShowMenu(

                        )
                        Spacer(Modifier.height(12.dp))
                        PlaceInfoShowMenu(

                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

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

@Composable
fun PlaceInfoAbstractView(
    placeInfoGroupName: String = "맛집그룹",
    placeInfoName: String = "맛집이름",
    placeInfoMenuListText: String = "알리올리오",
    placeInfoScore: Float = 1.4f,
    isKind: Boolean = true,
    isDelicious: Boolean = true,
    isMood: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextButton(
        shape = RoundedCornerShape(8.dp),
        onClick = {},
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
        ),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
            .background(
                color = Color(0xFFFFFEFE),
                shape = RoundedCornerShape(8.dp),
            )
            .shadow(5.dp),
    )
    {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "#$placeInfoGroupName",
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = placeInfoName,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = placeInfoMenuListText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Spacer(Modifier.height(12.dp))
            Row {
                for (i in 1..5) {
                    SingleRatingStar(
                        isClicked = i <= placeInfoScore.toInt(),
                        starSize = 20
                    )
                    Spacer(Modifier.width(2.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
            ) {
                if (isKind) {
                    RatedMode(
                        text = "친절함",
                        color = KindRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
                if (isDelicious) {
                    RatedMode(
                        text = "맛집",
                        color = DeliciousRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
                if (isMood) {
                    RatedMode(
                        text = "분위기",
                        color = MoodRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
            }
        }
    }
}