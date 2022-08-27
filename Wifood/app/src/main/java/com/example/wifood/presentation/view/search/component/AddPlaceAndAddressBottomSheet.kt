package com.example.wifood.presentation.view.search.component

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.search.SearchPlaceFormEvent
import com.example.wifood.presentation.view.search.SearchPlaceViewModel
import com.google.gson.Gson

@Composable
fun AddPlaceAndAddressBottomSheet(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    Surface(modifier = Modifier.height(600.dp)) {
        when (viewModel.formState.addPlaceContentPageCount) {
            1 -> {
                InputNameNameContent()
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
fun InputNameNameContent(
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "맛집 이름을 입력해주세요.")
        TextField(
            value = state.addPlaceName,
            onValueChange = { viewModel.onEvent(SearchPlaceFormEvent.AddPlaceNameChange(it)) }
        )
        MainButton(
            text = "다음",
            onClick = {
                viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
            }
        )
    }
}

@Composable
fun SelectAddressSearchWayContent(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "맛집 주소를 입력해주세요.")
        TextField(
            value = state.addPlaceAddressSearch,
            onValueChange = {
                viewModel.onEvent(SearchPlaceFormEvent.AddPlaceAddressChange(it))
            }
        )
        MainButton(
            text = "주소 검색",
            onClick = {
                viewModel.onEvent(SearchPlaceFormEvent.AddressSearchButtonClick)
                viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
            }
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
    LazyColumn {
        items(state.addressSearchResults) { searchResult ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                Column {
                    Text(text = searchResult.fullAddress)
                    Text(text = searchResult.oldAddress)
                }
            }
        }
    }
}