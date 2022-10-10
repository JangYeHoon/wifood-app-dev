package com.example.wifood.presentation.view.main

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.presentation.util.*
import com.example.wifood.presentation.view.MySettingView
import com.example.wifood.presentation.view.component.BottomSheetContent
import com.example.wifood.presentation.view.component.MapTopAppBar
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.map.util.DefaultLocationClient
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.presentation.view.placeList.PlaceListComposeView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainView(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var exitWaitTime = 0L
    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)
    val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val placeLng = navBackStackEntry.arguments?.getFloat("placeLng")!!
    val placeLat = navBackStackEntry.arguments?.getFloat("placeLat")!!
    if (placeLng != 10000f) {
        viewModel.onEvent(MainEvent.CameraMove(LatLng(placeLat.toDouble(), placeLng.toDouble())))
    }

    DisposableEffect(context) {
        val locationClient = DefaultLocationClient(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )

        locationClient
            .getLocationUpdates(3000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                MainData.location = location
                viewModel.onEvent(MainEvent.LocationChanged(location))
            }
            .launchIn(serviceScope)

        onDispose {
            serviceScope.cancel()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.init()
        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            scope.launch { modalBottomSheetState.hide() }
        } else {
            if (System.currentTimeMillis() - exitWaitTime >= 1500) {
                exitWaitTime = System.currentTimeMillis()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("한번 더 누르면 종료됩니다.")
                }
            } else {
                activity?.finish()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                state.selectedGroupSheet,
                navController,
                viewModel,
                modalBottomSheetState
            )
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
                }
            },
            bottomBar = {
                val items = listOf(
                    NavItem.Map,
                    NavItem.List,
                    NavItem.MyPage
                )

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
                            selected = state.selected == item.id,
                            selectedContentColor = Main,
                            unselectedContentColor = Color.Black,
                            onClick = {
                                if (state.selected != item.id) viewModel.onEvent(
                                    MainEvent.ItemClicked(
                                        item.id
                                    )
                                )
                            },
                            icon = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(painterResource(id = item.icon), contentDescription = null)
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
                    MapView(navController, placeLat)
                }
                NavItem.List.id -> {
                    PlaceListComposeView(modalBottomSheetState, navController)
                }
                NavItem.MyPage.id -> {
                    MySettingView(navController)
                }
            }
        }
    }
}

