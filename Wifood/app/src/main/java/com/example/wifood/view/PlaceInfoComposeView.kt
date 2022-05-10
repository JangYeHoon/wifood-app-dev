package com.example.wifood.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

class PlaceInfoComposeView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painterResource(id = R.drawable.plcae_image),
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
                        /*TODO*/
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
                            text = "#맛집 테마",
                            fontSize = 10.sp
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "맛집 이름",
                            fontSize = 15.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        LazyRow {
                            // TODO: items(메뉴) 리스트로 변경
                            item {
                                Text(
                                    text = "하와이안 피자",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        Row {
                            Text(
                                text = "맛",
                                fontSize = 12.sp
                            )
                            Text(
                                text = "4.2",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "청결",
                                fontSize = 12.sp
                            )
                            Text(
                                text = "4.2",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "친절",
                                fontSize = 12.sp
                            )
                            Text(
                                text = "4.2",
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Row {
                    Icon(
                        painterResource(id = R.drawable.ic_group_pin),
                        contentDescription = ""
                    )

                    Text(text = "서울시 용산구 효창동 522-2", fontSize = 13.sp)
                }

                GoogleMap(
                    modifier = Modifier
                        .size(250.dp, 55.dp),
                    cameraPositionState = rememberCameraPositionState {
                        position =
                            CameraPosition.fromLatLngZoom(LatLng(37.56253812, 126.9856316), 15f)
                    }
                ) {
                    Marker(
                        position = LatLng(37.56253812, 126.9856316),
                        title = "명동교자 본점"
                    )
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )

                Text(text = "메뉴", fontSize = 15.sp)

                LazyColumn {
                    // TODO: items(메뉴평가) 리스트로 변경
                    item {
                        Row {
                            Text(text = "알리오 올리오", fontSize = 12.sp)
                            Text(text = "18,000원", fontSize = 12.sp)
                        }
                        Text(text = "비린내", fontSize = 12.sp)
                    }
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )

                Text(text = "메모", fontSize = 15.sp)
                Text(text = "골목에 있어서 차 못들어감 참고하기", fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting()
}