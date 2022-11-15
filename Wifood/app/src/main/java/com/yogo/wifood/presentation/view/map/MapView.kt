package com.yogo.wifood.presentation.view.map

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.yogo.wifood.R
import com.yogo.wifood.data.remote.dto.PlaceDto
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.MainButtonToggle
import com.yogo.wifood.presentation.view.main.MainEvent
import com.yogo.wifood.presentation.view.main.MainViewModel
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.presentation.view.map.component.CustomMarker
import com.yogo.wifood.presentation.view.map.contents.MapSearchTextField
import com.yogo.wifood.presentation.view.map.contents.YOGOFloatingActionGroup
import com.yogo.wifood.presentation.view.map.util.Colors
import com.yogo.wifood.view.ui.theme.Main
import com.yogo.wifood.view.ui.theme.mapGroupListButtonIntervalValue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@RequiresApi(Build.VERSION_CODES.O)
@MapsComposeExperimentalApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapView(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val listState = rememberLazyListState()
    var selectedMenu by remember { mutableStateOf(-1) }
    val camera = rememberCameraPositionState {
        scope.launch {
            MainData.location?.let {
                position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 16f)
            }
        }
    }
    val context = LocalContext.current

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false
        )
    }

    LaunchedEffect(true) {
        viewModel.camera.collectLatest { latlng ->
            camera.position = CameraPosition.fromLatLngZoom(latlng, 16f)
        }

        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    val searchPlaceViewResult =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<TMapSearch>("searchResult")
            ?.observeAsState()

    LaunchedEffect(searchPlaceViewResult) {
        searchPlaceViewResult?.value?.let {
            scope.launch {
                viewModel.onEvent(MainEvent.SearchClicked(it))
                viewModel.onEvent(MainEvent.CameraMove(LatLng(it.latitude, it.longitude)))
            }
        }
    }

    var placeLng = navBackStackEntry.arguments?.getFloat("placeLng")!!
    var placeLat = navBackStackEntry.arguments?.getFloat("placeLat")!!
    if (placeLng != 10000f) {
        viewModel.onEvent(MainEvent.CameraMove(LatLng(placeLat.toDouble(), placeLng.toDouble())))
        placeLng = 10000f
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            YOGOFloatingActionGroup(
                onCurrentFloatingButtonClicked = {
                    if (MainData.location != null) {
                        val currentLocation = MainData.location!!
                        scope.launch {
                            camera.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        currentLocation.latitude,
                                        currentLocation.longitude
                                    ), 16f
                                ), 1000
                            )
                        }
                    }
                },
                onAddLocationFloatingButtonClicked = {
                    val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                    navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    //color = Color(0xFFEDEDD9)
                    color = Color(0xFF000000)
                )
        ) {
            MapSearchTextField(
                searchText = viewModel.state.searchResultName,
                onFindLocationClicked = {
                    viewModel.state.searchResultLatLng?.let {
                        viewModel.onEvent(MainEvent.CameraMove(it))
                    }
                },
                onSearchLocationClicked = {
                    val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                    navController.navigate("${Route.Search.route}/${placeJson}")
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    properties = state.properties,
                    uiSettings = uiSettings,
                    cameraPositionState = camera,
                ) {
                    state.places.forEach { place ->
                        CustomMarker(
                            context = context,
                            position = LatLng(place.latitude, place.longitude),
                            title = place.name,
                            review = place.review,
                            rating = place.score.toInt(),
                            visible = if (selectedMenu == -1) true else selectedMenu == place.groupId,
                            iconResourceId = setResource(place.groupId % 10)
                        )
                    }
                    state.currentLocation?.let {
                        CustomMarker(
                            context = context,
                            position = LatLng(it.latitude, it.longitude),
                            title = "현재 위치",
                            iconResourceId = R.drawable.ic_current_user_location_icon
                        )
                    }
                    state.searchResultLatLng?.let {
                        Marker(
                            rememberMarkerState(position = LatLng(it.latitude, it.longitude)),
                            visible = true,
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                        )
                    }
                }
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = Color(0xB2FFFFFF),
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 5.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        MainButtonToggle(
                            text = "전체",
                            isClicked = -1 == selectedMenu,
                            modifier = Modifier,
                            buttonColor = if (selectedMenu == -1) Main else Color.White,
                            onClick = {
                                selectedMenu = -1
                                viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                            },
                            borderColor = Main
                        )
                        Spacer(Modifier.width(mapGroupListButtonIntervalValue.dp))
                    }
                    itemsIndexed(state.groups) { i, group ->
                        MainButtonToggle(
                            text = group.name,
                            isClicked = group.groupId == selectedMenu,
                            buttonColor = if (selectedMenu == group.groupId) setColor(selectedMenu) else Color.White,
                            onClick = {
                                selectedMenu = group.groupId
                                viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                                scope.launch {
                                    listState.animateScrollToItem(i)
                                }
                            },
                            borderColor = if (selectedMenu != group.groupId) setColor(group.groupId % 10) else Color.White.copy(
                                alpha = 0f
                            )
                        )
                        Spacer(Modifier.width(12.dp))
                    }
                }
            }
        }
    }
}

fun setResource(
    number: Int = 1
): Int {
    when (number) {
        1 -> {
            return Colors.One.res
        }
        2 -> {
            return Colors.Two.res
        }
        3 -> {
            return Colors.Three.res
        }
        4 -> {
            return Colors.Four.res
        }
        5 -> {
            return Colors.Five.res
        }
        6 -> {
            return Colors.Six.res
        }
        7 -> {
            return Colors.Seven.res
        }
        8 -> {
            return Colors.Eight.res
        }
        9 -> {
            return Colors.Nine.res
        }
        0 -> {
            return Colors.Ten.res
        }
        else -> {
            return Colors.One.res
        }
    }
}

fun setColor(
    number: Int = 1
): Color {
    var selectedColor = Color.White
    when (number) {
        1 -> {
            selectedColor = Color(Colors.One.main)
        }
        2 -> {
            selectedColor = Color(Colors.Two.main)
        }
        3 -> {
            selectedColor = Color(Colors.Three.main)
        }
        4 -> {
            selectedColor = Color(Colors.Four.main)
        }
        5 -> {
            selectedColor = Color(Colors.Five.main)
        }
        6 -> {
            selectedColor = Color(Colors.Six.main)
        }
        7 -> {
            selectedColor = Color(Colors.Seven.main)
        }
        8 -> {
            selectedColor = Color(Colors.Eight.main)
        }
        9 -> {
            selectedColor = Color(Colors.Nine.main)
        }
        0 -> {
            selectedColor = Color(Colors.Ten.main)
        }
        else -> {
            selectedColor = Color(Colors.One.main)
        }
    }
    return selectedColor
}