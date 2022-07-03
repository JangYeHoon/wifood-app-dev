package com.example.wifood.presentation.view.placeList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.view.placeList.component.PlaceInfoBottomSheetContent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = if (state.placeImageUris.isNotEmpty())
                            state.placeImageUris[0]
                        else
                            R.drawable.plcae_image
                    ),
                    contentDescription = "",
                    Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                )

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

                Column(
                    modifier = Modifier.padding(35.dp)
                ) {
                    Spacer(Modifier.height(188.dp))
                    Card(
                        modifier = Modifier
                            .width(332.dp)
                            .height(125.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 21.dp,
                                    top = 15.dp,
                                    end = 21.dp,
                                    bottom = 18.dp
                                )
                        ) {
                            Text(
                                text = state.group!!.description,
                                fontSize = 10.sp
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = state.place!!.name,
                                fontSize = 15.sp
                            )

                            Spacer(Modifier.height(6.dp))

                            LazyRow {
                                // TODO: items(메뉴) 리스트로 변경
                                item {
                                    Text(
                                        text = state.place.menu,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(Modifier.height(14.dp))

                            Row {
                                Text(
                                    text = "평점",
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = state.place.score.toString(),
                                    fontSize = 16.sp
                                )
                                // TODO "함수로 빼서 받을까"
                                if (state.place.kindChk) {
                                    Text(
                                        text = "#친절",
                                        fontSize = 12.sp
                                    )
                                }
                                if (state.place.cleanChk) {
                                    Text(
                                        text = "#청결",
                                        fontSize = 12.sp
                                    )
                                }
                                if (state.place.tasteChk) {
                                    Text(
                                        text = "#맛",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Row {
                        Icon(
                            painterResource(id = R.drawable.ic_group_pin),
                            contentDescription = ""
                        )

                        Text(text = state.place!!.address, fontSize = 13.sp)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .size(300.dp, 300.dp),
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

                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )

                    Text(text = "메뉴", fontSize = 15.sp)

                    state.place!!.menuList.forEach {
                        Column() {
                            Row {
                                Text(text = it.name, fontSize = 12.sp)
                                Text(text = it.price.toString(), fontSize = 12.sp)
                            }
                            Text(text = it.memo, fontSize = 12.sp)
                        }
                    }

                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )

                    Text(text = "메모", fontSize = 15.sp)
                    Text(text = state.place.review, fontSize = 12.sp)
                }
            }
        }
    }
}