package com.yogo.wifood.presentation.view.placeList

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.R
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.presentation.util.*
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.presentation.view.placeList.component.PlaceWriteGroupsBottomSheetContent
import com.yogo.wifood.presentation.view.placeList.componentGroup.YOGOSubTextFieldWithButton
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.YOGOSubTextField
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteViewModel
import com.yogo.wifood.util.getActivity
import com.yogo.wifood.view.ui.theme.*
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
fun EditPlaceView(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val formState = viewModel.formState
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

    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    scope.launch {
                        viewModel.onEvent(
                            PlaceInfoWriteFormEvent.PlaceImagesAdd(uri)
                        )
                    }
                }
            }
        }

    fun checkPermission(permission: String) {
        if (context.checkPermission(permission)) {
            locationPermissionGranted = true
        } else {
            context.getActivity().shouldShowRationale(permission)
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
                    } else
                        navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
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
            scaffoldState = scaffoldState
        ) {
            if (formState.isLoading)
                ProgressIndicator()

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MyPageTopAppBar(
                    titleText = "",
                    leftButtonOn = true,
                    leftButtonClicked = {
                        navController.popBackStack()
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .verticalScroll(scrollState)
                        .padding(horizontal = sidePaddingValue.dp)
                ) {
                    Spacer(Modifier.height(58.dp))
                    YOGOLargeText("맛집 수정")
                    Spacer(Modifier.height(24.dp))
                    YOGOSubTextFieldWithButton(
                        titleText = "맛집 그룹",
                        inputText = formState.groupName,
                        onTextFieldClick = {
                            customSheetContent =
                                { PlaceWriteGroupsBottomSheetContent(modalBottomSheetState) }
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                    YOGOSubTextFieldWithButton(
                        titleText = "맛집 이름",
                        inputText = formState.placeName,
                        onValueChange = {},
                        onTextFieldClick = {
                            val placeJson = Uri.encode(Gson().toJson(viewModel.state.place))
                            navController.navigate("${Route.Search.route}/${placeJson}")
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                    YOGOTextPM15(text = "방문 여부")
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
                    YOGOTextPM15(text = "맛집 평가")
                    Spacer(Modifier.height(10.dp))
                    Row {
                        for (i in 0..4) {
                            SingleRatingStar(
                                isClicked = i < formState.score,
                                starSize = 27,
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(i))
                                    }
                                }
                            )
                            Spacer(Modifier.width(6.dp))
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                    ) {
                        RatedMode(
                            text = "위생",
                            color = KindRateColor,
                            clickable = formState.cleanChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.CleanCheck(!formState.cleanChk))
                                }
                            }
                        )
                        Spacer(Modifier.width(6.dp))
                        RatedMode(
                            text = "친절",
                            color = DeliciousRateColor,
                            clickable = formState.kindChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.KindCheck(!formState.kindChk))
                                }
                            }
                        )
                        Spacer(Modifier.width(6.dp))
                        RatedMode(
                            text = "분위기",
                            color = MoodRateColor,
                            clickable = formState.vibeChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.VibeCheck(!formState.vibeChk))
                                }
                            }
                        )
                        Spacer(Modifier.width(6.dp))
                        RatedMode(
                            text = "주차",
                            color = MoodRateColor,
                            clickable = formState.tasteChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.TasteCheck(!formState.tasteChk))
                                }
                            }
                        )
                        Spacer(Modifier.width(6.dp))
                    }
                    Spacer(Modifier.height(34.dp))
                    YOGOTextPM15("맛집 리뷰")
                    Spacer(Modifier.height(10.dp))
                    ReviewTextField(
                        text = formState.review,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.ReviewChange(it))
                            }
                        },
                        modifier = Modifier
                            .height(108.dp),
                        fontSize = 15,
                        showCount = true
                    )
                    Spacer(Modifier.height(10.dp))
                    PhotoListUpWithSelection(
                        formState.placeImages,
                        photoAddClick = {
                            scope.launch {
                                takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                            }
                        }
                    )
                    Spacer(Modifier.height(34.dp))
                    formState.menuGrades.forEachIndexed { index, menuGrade ->
                        YOGOSubTextField(
                            titleText = "메뉴명",
                            inputText = menuGrade.name,
                            placeholder = "메뉴명을 입력해주세요",
                            onValueChange = {
                                scope.launch {
                                    viewModel.onEvent(
                                        PlaceInfoWriteFormEvent.MenuNameChange(
                                            index,
                                            it
                                        )
                                    )
                                }
                            }
                        )
                        Spacer(Modifier.height(24.dp))
                        YOGOSubTextField(
                            titleText = "가격",
                            inputText = if (menuGrade.price == 0) "" else menuGrade.price.toString(),
                            placeholder = "가격을 입력해주세요",
                            onValueChange = {
                                scope.launch {
                                    viewModel.onEvent(
                                        PlaceInfoWriteFormEvent.MenuPriceChange(
                                            index,
                                            it
                                        )
                                    )
                                }
                            },
                            transformEnable = true
                        )
                        Spacer(Modifier.height(24.dp))
                        YOGOSubTextField(
                            titleText = "메뉴 리뷰",
                            inputText = menuGrade.memo,
                            placeholder = "메뉴 리뷰를 입력해주세요",
                            onValueChange = {
                                scope.launch {
                                    viewModel.onEvent(
                                        PlaceInfoWriteFormEvent.MenuMemoChange(
                                            index,
                                            it
                                        )
                                    )
                                }
                            }
                        )
                        Spacer(Modifier.height(24.dp))
                    }
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_add_menu_eval_button),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.MenuGradeAddBtnClick)
                                }
                            },
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.height(59.dp))
                    Spacer(Modifier.weight(1f))
                    MainButton(
                        text = "맛집 수정하기",
                        onClick = {
                            scope.launch {
                                if (viewModel.checkForm())
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceEditBtnClick)
                            }
                        },
                        activate = viewModel.checkForm() && formState.placeName != "맛집 선택" && formState.groupName != "그룹 선택",
                    )
                    Spacer(Modifier.height(buttonBottomValue.dp))
                }
            }
        }
    }
}