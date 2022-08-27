package com.example.wifood.presentation.view.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapSearchAddressView(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng((-34).toDouble(), 151.toDouble()), 1f)
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
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "위치 등록하기")
        }
    }
}