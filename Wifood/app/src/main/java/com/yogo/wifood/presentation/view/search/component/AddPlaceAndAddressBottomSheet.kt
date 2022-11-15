package com.yogo.wifood.presentation.view.search.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.DialogCenterDivider
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.FindAddressOnMapButton
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.PointLocationAddress2
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.PutPlaceAddressTextField
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.PutPlaceNameTextField
import com.yogo.wifood.presentation.view.search.SearchPlaceFormEvent
import com.yogo.wifood.presentation.view.search.SearchPlaceViewModel
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun AddPlaceAndAddressBottomSheet(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    Surface(modifier = Modifier.height(692.dp)) {
        when (viewModel.formState.addPlaceContentPageCount) {
            1 -> {
                InputNameContent()
            }
            2 -> {
                SelectAddressSearchWayContent(navController)
            }
            3 -> {
                AddressSearchResultContent()
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
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = sidePaddingValue.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.weight(1f))
        YOGOLargeText(
            text = "맛집 이름을\n입력해주세요."
        )
        Spacer(Modifier.height(24.dp))
        PutPlaceNameTextField(
            text = state.addPlaceName,
            onValueChange = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.AddPlaceNameChange(it))
                }
            },
            onDeleteBtnClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.InputNameClear)
                }
            },
            onNextBtnClick = {
                if (state.addPlaceName.isNotEmpty()) {
                    scope.launch {
                        viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
                    }
                }
            }
        )
        Spacer(Modifier.weight(1f))
        MainButton(
            text = "다음",
            onClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
                }
            },
            activate = state.addPlaceName.isNotEmpty()
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
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
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = sidePaddingValue.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.weight(1f))
        YOGOLargeText(
            text = "맛집 주소를\n입력해주세요."
        )
        Spacer(Modifier.height(24.dp))
        PutPlaceAddressTextField(
            text = state.addPlaceAddressSearch,
            onValueChange = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.AddPlaceAddressChange(it))
                }
            },
            onSearchBtnClick = {
                if (state.addPlaceAddressSearch.isNotEmpty()) {
                    scope.launch {
                        viewModel.onEvent(SearchPlaceFormEvent.AddressSearchButtonClick)
                        viewModel.onEvent(SearchPlaceFormEvent.ClickNextBtn)
                    }
                }
            },
            onDeleteBtnClick = {
                scope.launch {
                    viewModel.onEvent(SearchPlaceFormEvent.InputAddressClear)
                }
            }
        )
        Spacer(Modifier.height(16.dp))
        FindAddressOnMapButton(
            onClick = {
                val placeJson = Uri.encode(Gson().toJson(viewModel.formState.place))
                navController.navigate("${Route.MapSearchAddress.route}/${placeJson}")
            }
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun AddressSearchResultContent(
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val state = viewModel.formState
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = sidePaddingValue.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.weight(1f))
        YOGOLargeText(
            text = "맛집 주소를\n입력해주세요."
        )
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            state.addressSearchResults.forEachIndexed { index, searchResult ->
                PointLocationAddress2(
                    buildingAddress = searchResult.oldAddress,
                    roadAddress = searchResult.fullAddress,
                    function = {
                        scope.launch {
                            viewModel.onEvent(
                                SearchPlaceFormEvent.AddressClick(
                                    searchResult,
                                    index
                                )
                            )
                        }
                    },
                    isClicked = state.clickedAddressIdx == index
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = {
                    scope.launch {
                        viewModel.onEvent(SearchPlaceFormEvent.InputAddressViewBtnClick)
                    }
                },
                activate = state.clickedAddressIdx != -1
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}