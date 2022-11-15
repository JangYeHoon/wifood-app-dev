package com.yogo.wifood.presentation.view.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.data.remote.dto.PlaceDto
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.main.MainEvent
import com.yogo.wifood.presentation.view.main.MainViewModel
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.Main
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MapTopAppBar(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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

    TopAppBar(
        title = {
            Text(
                text = viewModel.state.searchResultName,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray01Color
            )
        },
        modifier = Modifier
            .height(48.dp)
            .clickable {
                val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                navController.navigate("${Route.Search.route}/${placeJson}")
            },
        navigationIcon = {
            IconButton(
                onClick = {
                    viewModel.state.searchResultLatLng?.let {
                        viewModel.onEvent(MainEvent.CameraMove(it))
                    }
                }
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "Pin",
                    tint = Main,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            Row(modifier = Modifier.width(50.dp)) {
                IconButton(onClick = {
                    val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
                    navController.navigate("${Route.Search.route}/${placeJson}")
                }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                    )
                }
            }
        }
    )
}