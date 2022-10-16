package com.example.wifood.presentation.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButtonToggle
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.presentation.view.main.UiEvent
import com.example.wifood.presentation.view.main.conponents.YOGOBottomBar
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.map.component.CustomMarker
import com.example.wifood.presentation.view.map.util.Colors
import com.example.wifood.presentation.view.map.util.DefaultLocationClient
import com.example.wifood.presentation.view.map.util.LocationClient
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.mapGroupListButtonIntervalValue
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@MapsComposeExperimentalApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapView(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(true) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val listState = rememberLazyListState()
    var selectedMenu by remember { mutableStateOf(-1) }
    val camera = rememberCameraPositionState {
        scope.launch {
            delay(1000L)
            isLoading = false
            MainData.location?.let {
                position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 16f)
            }
        }
    }
    val scrollState = rememberScrollState()
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

    if (isLoading) {
        ProgressIndicator()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            YOGOFloatingActionGroup(
                onCurrentFloatingButtonClicked = {
                    val currentLocation = state.currentLocation!!
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
                    viewModel.onUiEvent(UiEvent.ShowSnackBar("${currentLocation.latitude.toString()}, ${currentLocation.longitude.toString()}"))
                },
                onAddLocationFloatingButtonClicked = {
                    val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                    navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
                }
            )
        },
        bottomBar = {
            YOGOBottomBar(
                selected = "map",
                pushMapClicked = {},
                pushListClicked = {},
                pushSettingClicked = {}
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

            )
            /*SelectPlaceGroupContent(

            )*/

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color(0xE6FFFFFF)
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
                        buttonColor = MainColor,
                        onClick = {
                            selectedMenu = -1
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                        }
                    )
                    Spacer(Modifier.width(mapGroupListButtonIntervalValue.dp))

                    /*
                    TextButton(
                        modifier = Modifier
                            .padding(5.dp)
                            .selectable(
                                selected = -1 == selectedMenu,
                                onClick = {
                                    selectedMenu = -1
                                    viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                                }
                            )
                            .height(32.dp),
                        onClick = {
                            selectedMenu = -1
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                        },
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, Main),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (selectedMenu == -1) Main else Color.White
                        )
                    ) {
                        Text(
                            text = "전체",
                            color = if (selectedMenu != -1) Main else Color.White,
                            style = TextStyle(
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        )
                    }*/
                }
                itemsIndexed(state.groups) { i, group ->
                    MainButtonToggle(
                        text = group.name,
                        isClicked = group.groupId == selectedMenu,
                        buttonColor = setColor(selectedMenu),
                        onClick = {
                            selectedMenu =
                                if (selectedMenu != group.groupId) group.groupId else -1
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                            scope.launch {
                                listState.animateScrollToItem(i)
                            }
                        }
                    )
                    Spacer(Modifier.width(12.dp))
                    /*
                    TextButton(
                        modifier = Modifier
                            .padding(5.dp)
                            .selectable(
                                selected = group.groupId == selectedMenu,
                                onClick = {
                                    selectedMenu =
                                        if (selectedMenu != group.groupId) group.groupId else -1
                                    viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                                }
                            )
                            .height(32.dp),
                        onClick = {
                            selectedMenu = if (selectedMenu != group.groupId) group.groupId else -1
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                            scope.launch {
                                listState.animateScrollToItem(i)
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(
                            1.dp, if (selectedMenu != group.groupId) setColor(group.groupId % 10) else Color.White.copy(alpha = 0f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (selectedMenu == group.groupId) setColor(selectedMenu) else Color.White
                        )
                    ) {
                        Text(
                            text = group.name,
                            color = if (selectedMenu != group.groupId) setColor(group.groupId % 10) else Color.White,
                            style = TextStyle(
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        )
                    }*/
                }
            }
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