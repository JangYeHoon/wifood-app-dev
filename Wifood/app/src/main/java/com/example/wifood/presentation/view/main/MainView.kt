package com.example.wifood.presentation.view.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.checkPermission
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.presentation.util.*
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.findActivity
import com.example.wifood.presentation.view.MyPageComposeView
import com.example.wifood.presentation.view.component.BottomSheetContent
import com.example.wifood.presentation.view.component.ListTopAppBar
import com.example.wifood.presentation.view.component.MapTopAppBar
import com.example.wifood.presentation.view.component.MyPageTAB
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.presentation.view.placeList.PlaceListComposeView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@ExperimentalPermissionsApi
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
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var locationPermissionGranted = false
    var lastKnownLocation: Location? = null

    fun checkPermission(permission: String) {
        if (context.checkPermission(permission)) {
            locationPermissionGranted = true
        } else {
            context.findActivity().shouldShowRationale(permission)
        }
    }

    LaunchedEffect(key1 = true) {
        // Modified later
        /*
           Take permission through popup message
        */
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        viewModel.onEvent(MainEvent.LocationChanged(task.result))
                    }
                }
            }
        } else {
            viewModel.onUiEvent(UiEvent.ShowSnackBar("Permission denied."))
        }
        viewModel.init()
        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(state.selectedGroupSheet, navController)
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
                        ListTopAppBar(navController)
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
                                viewModel.onEvent(
                                    MainEvent.CameraMove(
                                        LatLng(
                                            state.currentLocation!!.latitude,
                                            state.currentLocation!!.longitude
                                        )
                                    )
                                )
                            },
                            backgroundColor = Color.White,
                            contentColor = Main
                        ) {
                            Icon(Icons.Filled.CenterFocusStrong, contentDescription = null)
                        }
                        FloatingActionButton(
                            onClick = {
                                val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                                navController.navigate("${Route.EditPlace.route}/${placeJson}")
                            },
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
                                    MainEvent.ItemClicked(
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

