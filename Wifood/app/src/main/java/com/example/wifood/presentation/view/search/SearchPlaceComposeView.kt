package com.example.wifood.presentation.view.search

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.shouldShowRationale
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.search.component.AddPlaceAndAddressBottomSheet
import com.example.wifood.util.getActivity
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchPlaceComposeView(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    var locationPermissionGranted = false
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val searchClickChkForSearchResult = remember { mutableStateOf(false) }
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    fun checkPermission(permission: String) {
        if (context.checkPermission(permission)) {
            locationPermissionGranted = true
        } else {
            context.getActivity().shouldShowRationale(permission)
        }
    }

    LaunchedEffect(key1 = true) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        scope.launch {
                            viewModel.onEvent(SearchPlaceFormEvent.CurrentLocationChange(task.result))
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

    ModalBottomSheetLayout(
        sheetContent = { AddPlaceAndAddressBottomSheet(navController) },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.White
    ) {
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
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(
                                    SearchPlaceFormEvent.SearchKeywordChange(
                                        it
                                    )
                                )
                            }
                        },
                        modifier = Modifier.width(350.dp)
                    )
                    IconButton(onClick = {
                        scope.launch {
                            viewModel.onEvent(SearchPlaceFormEvent.SearchButtonClick)
                        }
                        searchClickChkForSearchResult.value = true
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "")
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(formState.searchResults) {
                        if (formState.searchResults[0].name == "") {
                            Text(text = "등록이 안된 식당")
                            MainButton(
                                text = "직접 등록하기",
                                onClick = {
                                    scope.launch {
                                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                            )
                        } else {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                                    .clickable {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "searchResult",
                                            it
                                        )
                                        navController.popBackStack()
                                    }
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
    }
}