package com.yogo.wifood.presentation.view.search

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.util.checkPermission
import com.yogo.wifood.presentation.util.shouldShowRationale
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.*
import com.yogo.wifood.util.getActivity
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MapSearchAddressView(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.498095, 127.027610), 1f)
    }
    val context = LocalContext.current
    var locationPermissionGranted = false
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val scope = rememberCoroutineScope()

    fun checkPermission(permission: String) {
        if (context.checkPermission(permission)) {
            locationPermissionGranted = true
        } else {
            context.getActivity().shouldShowRationale(permission)
        }
    }
    LaunchedEffect(true) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        scope.launch {
                            camera.move(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        task.result.latitude,
                                        task.result.longitude
                                    ), 18f
                                )
                            )
                            viewModel.onEvent(
                                SearchPlaceFormEvent.CameraMove(
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
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    val placeJson = Uri.encode(Gson().toJson(viewModel.formState.place))
                    navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
                }
                is ValidationEvent.Error -> {

                }
            }
        }
    }

    LaunchedEffect(!camera.isMoving) {
        scope.launch {
            viewModel.onEvent(SearchPlaceFormEvent.CameraMove(camera.position.target))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SpaceWithTextTop("맛집 주소 등록")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = state.properties,
                uiSettings = uiSettings,
                cameraPositionState = camera
            )

            Column(
                modifier = Modifier
                    .background(Color.Unspecified)
                    .padding(
                        horizontal = sidePaddingValue.dp,
                        vertical = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpaceWithTextBlack()
                Spacer(Modifier.weight(1f))
                MapIcon()
                Spacer(Modifier.weight(1f))
                PointLocationAddress(
                    coarseAddress = state.oldAddressGeocoding,
                    fineAddress = state.roadAddressGeocoding
                )
            }
        }
        SpaceWithTextBottom(
            onClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.GoogleMapLatLngBtnClick(camera.position.target))
                }
            }
        )
    }
}