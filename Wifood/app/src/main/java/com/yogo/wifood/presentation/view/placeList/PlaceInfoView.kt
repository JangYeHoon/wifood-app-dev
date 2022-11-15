package com.yogo.wifood.presentation.view.placeList

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.yogo.wifood.R
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.presentation.view.placeList.component.PlaceInfoBottomSheetContent
import com.yogo.wifood.presentation.view.placeList.componentGroup.PlaceInfoAbstractComponent
import com.yogo.wifood.presentation.view.placeList.componentGroup.PlaceInfoShowMenu
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
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
    placeInfoMenuListText: String = "알리올리오",
    placeInfoScore: Float = 1.4f,
    isKind: Boolean = true,
    isDelicious: Boolean = true,
    isMood: Boolean = false,
    onClick: () -> Unit = {}
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
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
            .background(color = Color(0xFFFFFEFE)),
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

@Composable
fun RatedMode(
    text: String = "기분",
    color: Color = Color(0xFFFFB154),
    clickable: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(
                color = if (clickable) Color(0xFFFFB154) else Color(0xFFCFCFCF),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 8.dp)
            .padding(vertical = 2.dp)
            .clickable {
                onClick()
            },
    ) {
        Text(
            text = "#$text",
            fontFamily = mainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PlaceInfoView(
    navController: NavController,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            scope.launch { modalBottomSheetState.hide() }
        } else if (state.popupImageIdx != -1) {
            viewModel.onEvent(PlaceInfoEvent.ClickPlaceImage(-1))
        } else {
            viewModel.onEvent(PlaceInfoEvent.ViewChangeEvent)
            navController.popBackStack(
                "${Route.Main.route}?placeLat={placeLat}&placeLng={placeLng}",
                false
            )
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            PlaceInfoBottomSheetContent(
                state.place,
                navController,
                modalBottomSheetState
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top Left Right Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(top = 15.dp)
                    .zIndex(10f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VectorIconWithNoInteraction(
                    resource = R.drawable.ic_place_info_back_button,
                    onClick = {
                        if (modalBottomSheetState.isVisible) {
                            scope.launch { modalBottomSheetState.hide() }
                        } else if (state.popupImageIdx != -1) {
                            viewModel.onEvent(PlaceInfoEvent.ClickPlaceImage(-1))
                        } else {
                            viewModel.onEvent(PlaceInfoEvent.ViewChangeEvent)
                            navController.popBackStack(
                                "${Route.Main.route}?placeLat={placeLat}&placeLng={placeLng}",
                                false
                            )
                        }
                    }
                )
                VectorIconWithNoInteraction(
                    resource = R.drawable.ic_place_info_option_button,
                    onClick = {
                        scope.launch {
                            modalBottomSheetState.show()
                        }
                    }
                )
            }

            // Main Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Top Image
                Image(
                    painter = rememberImagePainter(
                        data =
                        if (state.placeImageUris.isNotEmpty())
                            state.placeImageUris[0]
                        else
                            R.drawable.ic_place_info_photo_default
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .zIndex(-10f)
                )

                // Place abstract and location view
                PlaceInfoAbstractComponent(
                    placeInfoGroupName = state.group!!.name,
                    placeInfoName = state.place!!.name,
                    placeInfoMenuListText = state.place.menu,
                    placeInfoScore = state.place.score,
                    isClean = state.place.cleanChk,
                    isKind = state.place.kindChk,
                    isMood = state.place.vibeChk,
                    isParking = state.place.tasteChk,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .offset(y = (-30).dp)
                )

                // Show Location
                Row(
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(PlaceInfoEvent.ViewChangeEvent)
                            navController.navigate("${Route.Main.route}?placeLat=${state.place.latitude}&placeLng=${state.place.longitude}")
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
                        text = state.place.address,
                        fontFamily = mainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Gray01Color
                    )
                }
                Spacer(Modifier.height(10.dp))
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = sidePaddingValue.dp)
                        .height(82.dp),
                    cameraPositionState = rememberCameraPositionState {
                        position =
                            CameraPosition.fromLatLngZoom(
                                LatLng(
                                    state.place.latitude,
                                    state.place.longitude
                                ), 15f
                            )
                    },
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ) {
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(
                                state.place.latitude,
                                state.place.longitude
                            )
                        ),
                        title = state.place.name
                    )
                }
                Spacer(Modifier.height(18.dp))
                // Divider if review or something exists
                CustomDivider(
                    fillColor = Color(0xFFF4F4F4),
                    borderColor = Color(0xFFE7E7E7),
                    thickness = 4
                )

                // Reviews if it exists
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        )
                ) {
                    if (state.placeReview.isNotEmpty()) {
                        Spacer(Modifier.height(18.dp))
                        Text(
                            text = "맛집 리뷰",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Gray01Color
                        )
                        Spacer(Modifier.height(8.dp))
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = state.placeReview,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Gray01Color
                        )
                    }
                    if (state.placeImageUris.isNotEmpty()) {
                        Spacer(Modifier.height(20.dp))
                        ShowPhotoList(
                            state.placeImageUris,
                            imageSize = 80
                        )
                        Spacer(Modifier.height(20.dp))
                    }
                    if (state.place.menuList.isNotEmpty()){
                        Spacer(Modifier.height(18.dp))
                        Text(
                            text = "메뉴 리뷰",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Gray01Color
                        )
                        Spacer(Modifier.height(8.dp))
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        state.place.menuList.forEach {
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                SpaceBetween
                            ){
                                Text(
                                    text = it.name,
                                    fontFamily = mainFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Gray01Color
                                )
                                Text(
                                    text = it.price.toString() + "원",
                                    fontFamily = mainFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Gray01Color
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = it.memo,
                                fontFamily = mainFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Gray01Color
                            )
                            Spacer(Modifier.height(10.dp))
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}