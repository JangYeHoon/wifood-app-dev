package com.example.wifood.presentation.view.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wifood.presentation.util.NavItem
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.MyPageComposeView
import com.example.wifood.presentation.view.component.BottomSheetContent
import com.example.wifood.presentation.view.component.ListTopAppBar
import com.example.wifood.presentation.view.component.MapTopAppBar
import com.example.wifood.presentation.view.component.MyPageTAB
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.presentation.view.placeList.PlaceListComposeView

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainView(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent()
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                when (state.selected) {
                    NavItem.Map.id -> {
                        MapTopAppBar(navController)
                    }
                    NavItem.List.id -> {
                        ListTopAppBar()
                    }
                    NavItem.MyPage.id -> {
                        MyPageTAB()
                    }
                }
            },
            floatingActionButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val isMapView = state.selected == NavItem.Map.id
                    if (isMapView) {
                        FloatingActionButton(
                            onClick = {
//                        camera.position = CameraPosition.fromLatLngZoom(
//                            LatLng(35.toDouble(), 128.toDouble()),
//                            15f
//                        )
                            },
                            backgroundColor = Color.White,
                            contentColor = Main
                        ) {
                            Icon(Icons.Filled.CenterFocusStrong, contentDescription = null)
                        }
                        FloatingActionButton(
                            onClick = { navController.navigate(Route.EditPlace.route) },
                            backgroundColor = Main,
                            contentColor = Color.White,
                            modifier = Modifier.size(75.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }
                    }
                }
            },
            bottomBar = {
                val items = listOf(
                    NavItem.Map,
                    NavItem.List,
                    NavItem.MyPage
                )
                var current by remember {
                    mutableStateOf(NavItem.Map.id)
                }

                BottomNavigation(
                    modifier = Modifier
                        .graphicsLayer {
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            clip = true
                        }
                        .height(72.dp),
                    backgroundColor = Color.White
                ) {
                    items.forEach { item ->
                        BottomNavigationItem(
                            selected = current == item.id,
                            selectedContentColor = Main,
                            unselectedContentColor = Color.Black,
                            onClick = {
                                if (current != item.id) viewModel.onEvent(
                                    MainEvent.itemClicked(
                                        item.id
                                    )
                                )
                                current = item.id
                            },
                            icon = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(item.icon, contentDescription = null)
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = item.title,
                                        style = TextStyle(
                                            fontFamily = robotoFamily,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        ) {
            when (state.selected) {
                NavItem.Map.id -> {
                    MapView(navController)
                }
                NavItem.List.id -> {
                    PlaceListComposeView(modalBottomSheetState, navController)
                }
                NavItem.MyPage.id -> {
                    MyPageComposeView(navController)
                }
            }
        }
    }
}
