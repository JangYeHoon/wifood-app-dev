package com.example.wifood.presentation.view.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.SingleRatingStar
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.presentation.view.map.component.MarkerCustomInfoWindow
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.example.wifood.view.ui.theme.MainColor
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

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
    var selectedMenu by remember { mutableStateOf(0) }
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng((-34).toDouble(), 151.toDouble()), 1f)
    }
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun moveCameraCurrentLocation() {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result != null) {
                    viewModel.onEvent(MainEvent.LocationChanged(task.result))
                    if (placeLat == 10000f) {
                        viewModel.onEvent(
                            MainEvent.CameraMove(
                                LatLng(
                                    task.result.latitude,
                                    task.result.longitude
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                moveCameraCurrentLocation()
            } else {
                Timber.i("false")
            }
        }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false
        )
    }

    LaunchedEffect(true) {
        // Modified later
        /*
           Take permission through popup message
        */
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                moveCameraCurrentLocation()
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(key1 = camera) {
        viewModel.camera.collectLatest { latlng ->
            camera.position = CameraPosition.fromLatLngZoom(latlng, 16f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = state.properties,
            uiSettings = uiSettings,
            cameraPositionState = camera
        ) {
//            CustomMarker(
//                context = context,
//                position = LatLng(35.0, 129.0),
//                title = "",
//                iconResourceId = R.drawable.ic_splash_icon
//            )
            state.places.forEach { place ->
                MarkerInfoWindow(
                    rememberMarkerState(position = LatLng(place.latitude, place.longitude)),
                    title = place.name,
                    visible = state.selectedGroupId == 0 || place.groupId == state.selectedGroupId,
                    snippet = place.review,
                    content = {
                        MarkerCustomInfoWindow(place.name, place.review, place.score.toInt())
                    }
                )
            }
            state.currentLocation?.let {
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
                        selected = 0 == selectedMenu,
                        onClick = {
                            selectedMenu = 0
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
//                                val location = LatLng(grouplatitude, place.longitude)
//                                builder.include(location)
//                                camera.move(CameraUpdateFactory.newLatLngBounds(builder.build(), 64))
                        }
                    )
                    .height(32.dp),
                onClick = {
                    selectedMenu = 0
                    viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                },
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Main),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (selectedMenu == 0) Main else Color.White
                )
            ) {
                Text(
                    text = "전체",
                    color = if (selectedMenu == 0) Color.White else Main,
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
                            selectedMenu = if (selectedMenu != group.groupId) group.groupId else 0
                            viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
//                                val location = LatLng(grouplatitude, place.longitude)
//                                builder.include(location)
//                                camera.move(CameraUpdateFactory.newLatLngBounds(builder.build(), 64))
                        }
                    )
                    .height(32.dp),
                onClick = {
                    selectedMenu = if (selectedMenu != group.groupId) group.groupId else 0
                    viewModel.onEvent(MainEvent.GroupClicked(selectedMenu))
                    scope.launch {
                        listState.animateScrollToItem(i)
                    }
                },
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Main),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (selectedMenu == group.groupId) Main else Color.White
                )
            ) {
                Text(
                    text = group.name,
                    color = if (selectedMenu == group.groupId) Color.White else Main,
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