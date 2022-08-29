package com.example.wifood.presentation.view.search

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.shouldShowRationale
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.UiEvent
import com.example.wifood.util.getActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
                            camera.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        task.result.latitude,
                                        task.result.longitude
                                    ), 15f
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
                    navController.navigate("${Route.AddNewPlaceComplete.route}/${placeJson}")
                }
                is ValidationEvent.Error -> {

                }
            }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = state.properties,
        uiSettings = uiSettings,
        cameraPositionState = camera
    )
    Box {
        Text(text = "맛집 위치를 설정하세요", modifier = Modifier.align(Alignment.TopCenter))
        Button(
            onClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.GoogleMapLatLngBtnClick(camera.position.target))
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "위치 등록하기")
        }
    }
}