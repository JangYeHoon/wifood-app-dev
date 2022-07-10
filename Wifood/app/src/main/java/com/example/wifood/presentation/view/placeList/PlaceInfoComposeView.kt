package com.example.wifood.presentation.view.placeList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.view.placeList.component.PlaceInfoBottomSheetContent
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
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

@Preview(showBackground = true)
@Composable
fun PlaceInfoMainContent(
    placeInfoGroupName: String = "맛집그룹",
    placeInfoName: String = "맛집이름",
    placeInfoMenuListText: AnnotatedString = buildAnnotatedString { append("하와이안 피자") },
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

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun test() {
    val scrollState = rememberScrollState()
    val menu1: String = "하와이안 피자"
    val menu1_price = 16000
    val menu1_memo = "작지만 맛있다."
    val menu2: String = "화이트리구 파스타"
    val menu2_price = 5000
    val menu2_memo = "살짝 맵다"
    val menu3: String = "채끝 등심 스테이크"
    val menu3_price = 4000
    val menu3_memo = "너무 퍽퍽함"
    val memoText = "골목에 차 있어서 못들어감. 참고하기"
    val placeInfoScore = 1.5f
    val isKind = true
    val isDelicious = true
    val isMood = true
    val placeInfoGroupName = "맛집그룹"
    val placeInfoName = "맛집이름"
    val placeInfoLocationName = "서울시 용산구 효창동 522-2 1F"
    Scaffold(
    ) {
        Column(
            Modifier
                .verticalScroll(scrollState)
        ) {
            Image(
                painterResource(R.drawable.plcae_image),
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
                        ImageVector.vectorResource(id = R.drawable.ic_location_icon),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = placeInfoLocationName,
                        fontFamily = mainFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Gray01Color
                    )
                }
                Spacer(Modifier.height(10.dp))
                Image(
                    painterResource(R.drawable.plcae_image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                )
                /*
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(81.dp),
                    cameraPositionState = rememberCameraPositionState {
                        position =
                            CameraPosition.fromLatLngZoom(
                                LatLng(
                                    37.5788, 126.9949
                                ), 15f
                            )
                    }
                ) {
                    Marker(
                        position = LatLng(
                            37.5788,
                            126.9949
                        ),
                        title = "경복궁"
                    )
                }*/
            }
            Divider(
                modifier = Modifier.height(1.dp),
                color = Color(0xFFE7E7E7)
            )
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = menu1,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = PlaceInfoMenuTextColor
                        )
                        Text(
                            text = menu1_price.toString() + "원",
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = PlaceInfoMenuTextColor
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = menu1_memo,
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Gray01Color
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
            Spacer(Modifier.height(20.dp))
            Divider(
                modifier = Modifier.height(1.dp),
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
                    text = memoText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray01Color
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }

        Column(
            Modifier
                .padding(horizontal = 14.dp)
                .padding(top = 188.dp)
                .fillMaxWidth()
        ) {
            PlaceInfoMainContent(
                placeInfoGroupName = placeInfoGroupName,
                placeInfoName = placeInfoName,
                placeInfoMenuListText = buildAnnotatedString {
                    append("$menu1, ")
                    append("$menu2, ")
                    append("$menu3, ")
                },
                placeInfoScore = placeInfoScore,
                isKind = isKind,
                isDelicious = isDelicious,
                isMood = isMood
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PlaceInfoComposeView(
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
                Modifier
                    .verticalScroll(scrollState)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data =
                        if (state.placeImageUris.isNotEmpty())
                            state.placeImageUris[0]
                        else
                            R.drawable.plcae_image
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
                        }
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
                    modifier = Modifier.height(1.dp),
                    color = Color(0xFFE7E7E7)
                )
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
                    modifier = Modifier.height(1.dp),
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

            Column(
                Modifier
                    .padding(horizontal = 14.dp)
                    .padding(top = 188.dp)
                    .fillMaxWidth()
            ) {
                val menu1: String = "에훈이"
                val menu2: String = "너가"
                val menu3: String = "넣어라"
                PlaceInfoMainContent(
                    placeInfoGroupName = state.group!!.description,
                    placeInfoName = state.place!!.name,
                    placeInfoMenuListText = buildAnnotatedString {
                        append("$menu1, ")
                        append("$menu2, ")
                        append("$menu3, ")
                    },
                    placeInfoScore = state.place.score,
                    isKind = state.place.kindChk,
                    isDelicious = state.place.tasteChk,
                    isMood = state.place.cleanChk
                )
            }

            Row(
                modifier = Modifier.padding(14.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .background(Color.White, CircleShape),
                    onClick = {
                        /*TODO*/
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, "")
                }

                Spacer(Modifier.size(275.dp))

                IconButton(
                    modifier = Modifier
                        .background(Color.White, CircleShape),
                    onClick = {
                        scope.launch {
                            modalBottomSheetState.show()
                        }
                    }
                ) {
                    Icon(Icons.Default.Menu, "")
                }
            }

        }
    }
}