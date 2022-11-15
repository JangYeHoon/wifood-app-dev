package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.R
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.util.checkPermission
import com.yogo.wifood.presentation.util.shouldShowRationale
import com.yogo.wifood.presentation.view.component.PlaceInputTopAppBar
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.presentation.view.component.YOGOSwitch
import com.yogo.wifood.presentation.view.component.YOGOTextPM15
import com.yogo.wifood.presentation.view.placeList.component.PlaceWriteGroupsBottomSheetContent
import com.yogo.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.YOGOSubTextFieldWithButton_SB
import com.yogo.wifood.util.getActivity
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputNameAndVisited(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val formState = viewModel.formState
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var locationPermissionGranted = false

    val sheetContent: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(sheetContent) }

    val searchPlaceViewResult =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<TMapSearch>("searchResult")
            ?.observeAsState()
    searchPlaceViewResult?.value?.let {
        scope.launch {
            viewModel.onEvent(PlaceInfoWriteFormEvent.SearchPlaceSelected(it))
        }
    }

    val placeBackStack =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Place>("placeBack")
            ?.observeAsState()
    placeBackStack?.value?.let {
        scope.launch {
            viewModel.onEvent(PlaceInfoWriteFormEvent.BackBtnClick(it))
        }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (formState.placeEditChk) {
                        val placeJson = Uri.encode(Gson().toJson(viewModel.state.place))
                        val groupJson = Uri.encode(Gson().toJson(viewModel.state.group))
                        navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                    } else {
                        navController.navigate(Route.AddNewPlaceComplete.route)
                    }
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            scope.launch { modalBottomSheetState.hide() }
        } else {
            navController.popBackStack(
                "${Route.Main.route}?placeLat={placeLat}&placeLng={placeLng}",
                false
            )
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
                            viewModel.onEvent(PlaceInfoWriteFormEvent.CurrentLocationChange(task.result))
                            Timber.i(task.result.toString())
                        }
                    }
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = { customSheetContent() },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFFFFFFFF)
    ) {
        Scaffold(
            topBar = {
                PlaceInputTopAppBar(
                    leftButtonClicked = {
                        navController.popBackStack()
                    },
                    rightButtonClicked = {
                        if (formState.groupName != "그룹 선택" && state.place.name.isNotEmpty()) {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                            }
                        }
                    },
                    rightButtonOn = true
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = sidePaddingValue.dp)
                ) {
                    Spacer(Modifier.weight(1f))
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_1by4),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.height(6.dp))
                    YOGOLargeText(
                        text = "맛집 정보를 등록해주세요.",
                    )
                    Spacer(Modifier.height(24.dp))
                    YOGOSubTextFieldWithButton_SB(
                        titleText = "맛집 그룹",
                        inputText = if (formState.groupName == "그룹 선택") "" else formState.groupName,
                        placeholder = "맛집 그룹을 입력해주세요",
                        onTextFieldClick = {
                            customSheetContent =
                                { PlaceWriteGroupsBottomSheetContent(modalBottomSheetState) }
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                    YOGOSubTextFieldWithButton_SB(
                        titleText = "맛집 이름",
                        inputText = state.place.name,
                        placeholder = "맛집 이름을 입력해주세요",
                        onTextFieldClick = {
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.Search.route}/${placeJson}")
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                    YOGOTextPM15(
                        text = "방문 여부"
                    )
                    YOGOSwitch(
                        checked = formState.visited,
                        onCheckedChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.VisitedCheck(it))
                            }
                        },
                        modifier = Modifier
                    )
                    Spacer(Modifier.height(24.dp))
                    Spacer(Modifier.weight(1f))
                    DoubleButton(
                        leftButtonText = "건너뛰기",
                        leftButtonOn = state.place.name.isNotEmpty() && formState.groupName != "그룹 선택",
                        leftButtonClicked = {
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.PlaceInputStarAndEvaluation.route}/${placeJson}")
                        },
                        rightButtonOn = state.place.name.isNotEmpty() && formState.groupName != "그룹 선택",
                        rightButtonText = "맛 평가하기",
                        rightButtonClicked = {
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.PlaceInputStarAndEvaluation.route}/${placeJson}")
                        }
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}