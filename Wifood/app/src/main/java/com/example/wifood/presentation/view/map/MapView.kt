package com.example.wifood.presentation.view.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wifood.presentation.util.NavItem
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.ui.theme.robotoFamily
import com.example.wifood.view.ui.theme.Main
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MapView(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember { MutableInteractionSource() }
    val interactions = remember { mutableStateListOf<Interaction>() }
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var selectedMenu by remember { mutableStateOf(0) }
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-34.toDouble(), 151.toDouble()), 1f)
    }
    val context = LocalContext.current
    val builder = LatLngBounds.Builder()

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false
        )
    }

    LaunchedEffect(true) {
        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = state.properties,
            uiSettings = uiSettings,
            cameraPositionState = camera
        ) {
            state.places.forEach { place ->
                Marker(
                    position = LatLng(place.latitude, place.longitude),
                    title = place.name,
                    visible = state.selectedGroupId == 0 || place.groupId == state.selectedGroupId,
                    snippet = place.review
                )
            }
        }
    }
    LazyRow(
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
                interactionSource = interactionSource,
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
        items(state.groups) { group ->
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
                },
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Main),
                interactionSource = interactionSource,
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


private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(android.graphics.Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}