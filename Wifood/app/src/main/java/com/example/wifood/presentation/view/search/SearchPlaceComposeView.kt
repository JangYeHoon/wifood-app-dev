package com.example.wifood.presentation.view.search

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.shouldShowRationale
import com.example.wifood.presentation.view.login.new_compose_views.CustomTextField
import com.example.wifood.presentation.view.login.new_compose_views.SearchPlaceInfoCard
import com.example.wifood.presentation.view.search.component.AddPlaceAndAddressBottomSheet
import com.example.wifood.presentation.view.search.newSearchComposeView.SearchPlaceEmptyView
import com.example.wifood.util.getActivity
import com.example.wifood.view.ui.theme.sidePaddingValue
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
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
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            if (viewModel.formState.addPlaceContentPageCount == 1) {
                scope.launch { modalBottomSheetState.hide() }
            } else {
                scope.launch { viewModel.onEvent(SearchPlaceFormEvent.BackBtnClick) }
            }
        } else {
            navController.popBackStack()
        }
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomTextField(
                address = formState.searchKeyword,
                onValueChanged = {
                    scope.launch {
                        viewModel.onEvent(
                            SearchPlaceFormEvent.SearchKeywordChange(
                                it
                            )
                        )
                    }
                },
                onDeleteClicked = {
                    scope.launch {
                        viewModel.onEvent(SearchPlaceFormEvent.SearchKeywordChange(""))
                    }
                },
                onSearchClicked = {
                    scope.launch {
                        viewModel.onEvent(SearchPlaceFormEvent.SearchButtonClick)
                    }
                    searchClickChkForSearchResult.value = true
                    keyboardController?.hide()
                },
                onBackClicked = { navController.popBackStack() },
                placeholder = "맛집, 주소 검색",
                keyboardActions = KeyboardActions(
                    onSearch = {
                        scope.launch {
                            viewModel.onEvent(SearchPlaceFormEvent.SearchButtonClick)
                        }
                        searchClickChkForSearchResult.value = true
                        keyboardController?.hide()
                    }
                )
            )
            Spacer(Modifier.height(12.dp))
            if (formState.searchResults.isNotEmpty() && formState.searchResults[0].name != "") {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = sidePaddingValue.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(formState.searchResults) {
                        SearchPlaceInfoCard(
                            address = it.name,
                            name = it.fullAddress,
                            search = it.bizName,
                            onClick = {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "searchResult",
                                    it
                                )
                                navController.popBackStack()
                            }
                        )
                    }
                }
            } else {
                SearchPlaceEmptyView(
                    onButtonClick = {
                        scope.launch {
                            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                )
            }
        }
    }
}