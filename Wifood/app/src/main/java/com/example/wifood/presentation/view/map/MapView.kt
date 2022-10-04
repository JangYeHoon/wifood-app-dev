package com.example.wifood.presentation.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.presentation.view.main.UiEvent
import com.example.wifood.presentation.view.map.component.CustomMarker
import com.example.wifood.presentation.view.map.util.Colors
import com.example.wifood.presentation.view.map.util.DefaultLocationClient
import com.example.wifood.presentation.view.map.util.LocationClient
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.S)
@MapsComposeExperimentalApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapView(
    navController: NavController,
    placeLat: Float,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val listState = rememberLazyListState()
    var selectedMenu by remember { mutableStateOf(-1) }
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng((-34).toDouble(), 151.toDouble()), 10f)
    }
    val context = LocalContext.current
    val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {
                Timber.i("false")
            }
        }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false
        )
    }

    DisposableEffect(context) {
        val locationClient = DefaultLocationClient(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )

        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                viewModel.onEvent(MainEvent.LocationChanged(location))
                viewModel.onUiEvent(UiEvent.ShowSnackBar("${location.latitude.toString()}, ${location.longitude.toString()}"))
            }
            .launchIn(serviceScope)

        onDispose {
            serviceScope.cancel()
        }
    }

    LaunchedEffect(true) {
        viewModel.camera.collectLatest { latlng ->
            camera.position = CameraPosition.fromLatLngZoom(latlng, 16f)
        }

        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    onClick = {
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
                    backgroundColor = Color.White,
                    contentColor = Main
                ) {
                    Icon(Icons.Filled.CenterFocusStrong, contentDescription = null)
                }
                FloatingActionButton(
                    onClick = {
                        val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                        navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
                    },
                    backgroundColor = Main,
                    contentColor = Color.White,
                    modifier = Modifier.size(75.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = state.properties,
                uiSettings = uiSettings,
                cameraPositionState = camera
            ) {
                state.places.forEach { place ->
                    CustomMarker(
                        context = context,
                        position = LatLng(place.latitude, place.longitude),
                        title = place.name,
                        review = place.review,
                        rating = place.score.toInt(),
                        visible = if (selectedMenu == -1) true else selectedMenu == place.groupId,
                        iconResourceId = when (place.groupId % 10) {
                            1 -> {
                                Colors.One.res
                            }
                            2 -> {
                                Colors.Two.res
                            }
                            3 -> {
                                Colors.Three.res
                            }
                            4 -> {
                                Colors.Four.res
                            }
                            5 -> {
                                Colors.Five.res
                            }
                            6 -> {
                                Colors.Six.res
                            }
                            7 -> {
                                Colors.Seven.res
                            }
                            8 -> {
                                Colors.Eight.res
                            }
                            9 -> {
                                Colors.Nine.res
                            }
                            0 -> {
                                Colors.Ten.res
                            }
                            else -> {
                                Colors.One.res
                            }
                        }
                    )
                }
                state.currentLocation?.let {
                    CustomMarker(
                        context = context,
                        position = LatLng(it.latitude, it.longitude),
                        title = "CL",
                        iconResourceId = R.drawable.ic_current_location
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
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White.copy(alpha = 0.8f))
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
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
                }
            }
            itemsIndexed(state.groups) { i, group ->
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
                        1.dp, if (selectedMenu != group.groupId) when (group.groupId % 10) {
                            1 -> {
                                Color(Colors.One.main)
                            }
                            2 -> {
                                Color(Colors.Two.main)
                            }
                            3 -> {
                                Color(Colors.Three.main)
                            }
                            4 -> {
                                Color(Colors.Four.main)
                            }
                            5 -> {
                                Color(Colors.Five.main)
                            }
                            6 -> {
                                Color(Colors.Six.main)
                            }
                            7 -> {
                                Color(Colors.Seven.main)
                            }
                            8 -> {
                                Color(Colors.Eight.main)
                            }
                            9 -> {
                                Color(Colors.Nine.main)
                            }
                            0 -> {
                                Color(Colors.Ten.main)
                            }
                            else -> {
                                Color(Colors.One.main)
                            }
                        } else Color.White.copy(alpha = 0f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (selectedMenu == group.groupId) when (selectedMenu) {
                            1 -> {
                                Color(Colors.One.main)
                            }
                            2 -> {
                                Color(Colors.Two.main)
                            }
                            3 -> {
                                Color(Colors.Three.main)
                            }
                            4 -> {
                                Color(Colors.Four.main)
                            }
                            5 -> {
                                Color(Colors.Five.main)
                            }
                            6 -> {
                                Color(Colors.Six.main)
                            }
                            7 -> {
                                Color(Colors.Seven.main)
                            }
                            8 -> {
                                Color(Colors.Eight.main)
                            }
                            9 -> {
                                Color(Colors.Nine.main)
                            }
                            0 -> {
                                Color(Colors.Ten.main)
                            }
                            else -> {
                                Color(Colors.One.main)
                            }
                        } else Color.White
                    )
                ) {
                    Text(
                        text = group.name,
                        color = if (selectedMenu != group.groupId) when (group.groupId % 10) {
                            1 -> {
                                Color(Colors.One.main)
                            }
                            2 -> {
                                Color(Colors.Two.main)
                            }
                            3 -> {
                                Color(Colors.Three.main)
                            }
                            4 -> {
                                Color(Colors.Four.main)
                            }
                            5 -> {
                                Color(Colors.Five.main)
                            }
                            6 -> {
                                Color(Colors.Six.main)
                            }
                            7 -> {
                                Color(Colors.Seven.main)
                            }
                            8 -> {
                                Color(Colors.Eight.main)
                            }
                            9 -> {
                                Color(Colors.Nine.main)
                            }
                            0 -> {
                                Color(Colors.Ten.main)
                            }
                            else -> {
                                Color(Colors.One.main)
                            }
                        } else Color.White,
                        style = TextStyle(
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }
    }
}