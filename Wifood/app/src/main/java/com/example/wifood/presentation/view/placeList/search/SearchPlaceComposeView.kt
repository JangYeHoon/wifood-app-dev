package com.example.wifood.presentation.view.placeList.search

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.findActivity
import com.example.wifood.presentation.util.shouldShowRationale
import com.google.android.gms.location.LocationServices

@Preview
@Composable
fun SearchPlaceComposeView(
    viewModel: SearchPlaceViewModel = hiltViewModel()
//    navController: NavController
) {
    val formState = viewModel.formState
    var locationPermissionGranted = false
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun checkPermission(permission: String) {
        if (context.checkPermission(permission)) {
            locationPermissionGranted = true
        } else {
            context.findActivity().shouldShowRationale(permission)
        }
    }

    LaunchedEffect(key1 = true) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        viewModel.onEvent(SearchPlaceFormEvent.CurrentLocationChange(task.result))
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(text = "장소 검색")
                    }
                },
                navigationIcon = {
                    IconButton(
//                        onClick = { navController.popBackStack() },
                        onClick = { },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                backgroundColor = Color.White,
                actions = {
                    Spacer(modifier = Modifier.width(70.dp))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = formState.searchKeyword,
                    onValueChange = { viewModel.onEvent(SearchPlaceFormEvent.SearchKeywordChange(it)) },
                    modifier = Modifier.width(350.dp)
                )
                IconButton(onClick = { viewModel.onEvent(SearchPlaceFormEvent.SearchButtonClick) }) {
                    Icon(Icons.Filled.Search, contentDescription = "")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(formState.searchResults) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Row {
                                Text(text = it.name, fontSize = 16.sp)
                                Text(text = it.bizName)
                            }
                            Text(text = it.fullAddress)
                        }
                    }
                }
            }
        }
    }
}