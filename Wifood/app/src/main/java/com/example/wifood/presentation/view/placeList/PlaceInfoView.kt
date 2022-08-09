package com.example.wifood.presentation.view.placeList

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.view.login.component.SnsIconButton
import com.example.wifood.presentation.view.placeList.component.PlaceInfoBottomSheetContent
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun PlaceInfoMenus(
    menuName: String = "하와이안 피자",
    menuPrice: Int = 16000,
    menuMemo: String = "작지만 맛있다."
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = menuName,
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = PlaceInfoMenuTextColor
            )
            Text(
                text = menuPrice.toString() + "원",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = PlaceInfoMenuTextColor
            )
        }
        Spacer(Modifier.height(5.dp))
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
fun PlaceInfoMainContent(
    placeInfoGroupName: String = "맛집그룹",
    placeInfoName: String = "맛집이름",
    placeInfoMenuListText: String,
    placeInfoScore: Float = 1.5f,
    isKind: Boolean = true,
    isDelicious: Boolean = true,
    isMood: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextButton(
        shape = RoundedCornerShape(25.dp),
        onClick = {},
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
    )
    {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .padding(vertical = 17.dp)
        ) {
            Text(
                text = "#$placeInfoGroupName",
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = placeInfoName,
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = placeInfoMenuListText,
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Spacer(Modifier.height(14.dp))
            Row(

            ) {
                Text(
                    text = buildAnnotatedString {
                        append("평점 ")
                        withStyle(
                            style = SpanStyle(
                                color = MainColor,
                                fontFamily = mainFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            ),
                        ) {
                            append(placeInfoScore.toString())
                        }
                        append("  ")
                        if (isKind)
                            append("#친절 ")
                        if (isDelicious)
                            append("#맛 ")
                        if (isMood)
                            append("#분위기 ")
                    },
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray01Color
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PlaceInfoView(
    navController: NavController,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = { PlaceInfoBottomSheetContent(state.place, navController) },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        val scrollState = rememberScrollState()

        Scaffold(
        ) {
            Column(
            ) {
                Image(
                    painter = rememberImagePainter(
                        data =
                        if (state.placeImageUris.isNotEmpty())
                            state.placeImageUris[0]
                        else
                            R.drawable.place_image
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                )

                // show google map
                Column(
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .padding(horizontal = 35.dp)
                        .padding(bottom = 25.dp)
                ) {
                    Row() {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_group_pin),
                            contentDescription = "",
                            modifier = Modifier.wrapContentSize(),
                            tint = Color.Unspecified
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            text = state.place!!.address,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Gray01Color
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        cameraPositionState = rememberCameraPositionState {
                            position =
                                CameraPosition.fromLatLngZoom(
                                    LatLng(
                                        state.place!!.latitude,
                                        state.place.longitude
                                    ), 15f
                                )
                        },
                        uiSettings = MapUiSettings(zoomControlsEnabled = false)
                    ) {
                        Marker(
                            position = LatLng(
                                state.place!!.latitude,
                                state.place.longitude
                            ),
                            title = state.place.name
                        )
                    }
                }
                Divider(
                    modifier = Modifier.height(2.dp),
                    color = Color(0xFFE7E7E7)
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(top = 20.dp)
                ){
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp)
                    ) {
                        items(state.placeImageUris) { image ->
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = image
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                            Spacer(Modifier.width(6.dp))
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp)
                            .padding(top = 29.dp)
                    ) {
                        Text(
                            text = "메뉴",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(5.dp))
                        state.place!!.menuList.forEach {
                            PlaceInfoMenus(
                                menuName = it.name,
                                menuPrice = it.price,
                                menuMemo = it.memo
                            )
                        }


                    }
                    Spacer(Modifier.height(20.dp))
                    Divider(
                        modifier = Modifier.height(2.dp),
                        color = PlaceInfoDividerColor
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                            .padding(horizontal = 35.dp)
                    ) {
                        Text(
                            text = "메모",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(15.dp))
                        Text(
                            text = state.place!!.review,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Gray01Color
                        )
                        Spacer(Modifier.height(buttonBottomValue.dp))
                    }
                }
            }

            Column(
                Modifier
                    .padding(horizontal = 14.dp)
                    .padding(top = 188.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp)
            ) {
                PlaceInfoMainContent(
                    placeInfoGroupName = state.group!!.name,
                    placeInfoName = state.place!!.name,
                    placeInfoMenuListText = state.place.menu,
                    placeInfoScore = state.place.score,
                    isKind = state.place.kindChk,
                    isDelicious = state.place.tasteChk,
                    isMood = state.place.cleanChk
                )
            }

            // put up buttons
            Box(
                Modifier
                    .padding(top = 15.dp)
                    .padding(horizontal = 15.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    SnsIconButton(
                        resourceId = R.drawable.ic_place_info_back_button,
                        size = 40,
                        onClick = {
                            // TODO("back button press event")
                            navController.popBackStack()
                        }
                    )
                    SnsIconButton(
                        resourceId = R.drawable.ic_place_info_option_button,
                        size = 40,
                        onClick = {
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    )

                }
            }
        }
    }
}