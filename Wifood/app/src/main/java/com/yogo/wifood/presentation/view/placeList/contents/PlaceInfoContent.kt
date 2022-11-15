package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.R
import com.yogo.wifood.data.remote.dto.MenuGradeDto
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.presentation.view.placeList.RatedMode
import com.yogo.wifood.presentation.view.placeList.componentGroup.PlaceInfoAbstractComponent
import com.yogo.wifood.presentation.view.placeList.componentGroup.PlaceInfoShowMenu
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PlaceInfoContent(
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
            PlaceInfoAbstractComponent(
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