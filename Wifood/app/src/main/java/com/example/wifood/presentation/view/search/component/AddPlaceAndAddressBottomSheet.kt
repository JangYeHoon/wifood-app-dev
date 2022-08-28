package com.example.wifood.presentation.view.search.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.search.SearchPlaceFormEvent
import com.example.wifood.presentation.view.search.SearchPlaceViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun AddPlaceAndAddressBottomSheet(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    Surface(modifier = Modifier.height(600.dp)) {
        when (viewModel.formState.addPlaceContentPageCount) {
            1 -> {
                InputNameContent()
            }
            2 -> {
                SelectAddressSearchWayContent(navController)
            }
            3 -> {
                AddressSearchResultContent(navController)
            }
        }
    }
}

@Composable
fun InputNameContent(
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "맛집 이름을 입력해주세요.")
        TextField(
            value = state.addPlaceName,
            onValueChange = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.AddPlaceNameChange(it))
                }
            }
        )
        MainButton(
            text = "다음",
            onClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
                }
            },
            activate = state.addPlaceName.isNotEmpty()
        )
    }
}

@Composable
fun SelectAddressSearchWayContent(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "맛집 주소를 입력해주세요.")
        TextField(
            value = state.addPlaceAddressSearch,
            onValueChange = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.AddPlaceAddressChange(it))
                }
            }
        )
        MainButton(
            text = "주소 검색",
            onClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.AddressSearchButtonClick)
                    viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
                }
            },
            activate = state.addPlaceAddressSearch.isNotEmpty()
        )
        MainButton(
            text = "지도에서 주소찾기",
            onClick = {
                navController.navigate(Route.MapSearchAddress.route)
            }
        )
    }
}

@Composable
fun AddressSearchResultContent(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val scope = rememberCoroutineScope()
    LazyColumn {
        items(state.addressSearchResults) { searchResult ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        scope.launch {
                            viewModel.onEvent(SearchPlaceFormEvent.AddressClick(searchResult))
                        }
                    }
            ) {
                Column {
                    Text(text = searchResult.fullAddress)
                    Text(text = searchResult.oldAddress)
                }
            }
        }
    }
}