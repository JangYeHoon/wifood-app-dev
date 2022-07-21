package com.example.wifood.presentation.view.placeList

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.placeList.component.ListSelectionButtonWithIcon
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.util.*
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.findActivity
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ThickDivider
import com.example.wifood.presentation.view.component_box.RatingWithText
import com.example.wifood.presentation.view.component_box.SingleIconWithText
import com.example.wifood.presentation.view.component_box.SwitchWithText
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.placeList.component.PlaceReviewInputText
import com.example.wifood.presentation.view.placeList.component.PlaceWriteGroupsBottomSheetContent
import com.example.wifood.view.ui.theme.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInfoWriteView(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var selectedArray = remember { mutableStateListOf<Int>(1, 0, 0, 0, 0) }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val placeInfoMenuImageSize = 60

    val formState = viewModel.formState
    val initialRating = 1f
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var locationPermissionGranted = false

    val googleSearchPlaceLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val searchResult = Autocomplete.getPlaceFromIntent(it.data)
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceSelected(searchResult))
                }
            }
        }
    val takePhotoFromCameraLauncher = // 카메라로 사진 찍어서 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val file = File(formState.currentPhotoPath)
                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceImagesAdd(Uri.fromFile(file)))
                }
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
            context.findActivity().shouldShowRationale(permission)
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
        sheetContent = { PlaceWriteGroupsBottomSheetContent() },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            topBar = {
                YOGOTopAppBar(
                    text = "맛집 등록",
                    onBackButtonClicked = {/*TODO*/ }
                )
            }
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {

                Column(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    ListSelectionButtonWithIcon(
                        buttonText = formState.groupName,
                        onClick = {
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    )
                    ListSelectionButtonWithIcon(
                        buttonText = formState.placeName,
                        onClick = {
                            val bounds =
                                viewModel.formState.currentLocation?.let {
                                    RectangularBounds.newInstance(
                                        LatLng(
                                            it.latitude,
                                            it.longitude
                                        ),
                                        LatLng(
                                            it.latitude,
                                            it.longitude
                                        )
                                    )
                                }

                            val googleSearchPlaceIntent =
                                Autocomplete.IntentBuilder(
                                    AutocompleteActivityMode.OVERLAY,
                                    viewModel.field
                                )
                                    .setLocationBias(bounds)
                                    .setCountry("KR")
                                    .build(context)
                            googleSearchPlaceLauncher.launch(googleSearchPlaceIntent)
                        }
                    )
                    SwitchWithText(
                        text = "방문 여부",
                        spaceBetweenSwitch = 14,
                        checked = formState.visited,
                        onCheckedChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.VisitedCheck(it))
                            }
                        }
                    )
                }
                ThickDivider()
                Column(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .padding(horizontal = 41.dp)
                        .padding(top = 25.dp)
                        .padding(bottom = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RatingWithText(
                        text = "어떤 점이 만족스러웠나요?",
                        formState.starScore
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SingleIconWithText(
                            text = "맛",
                            UnClickedSourceId = R.drawable.ic_place_info_taste_unclicked,
                            ClickedSourceId = R.drawable.ic_place_info_taste_clicked,
                            isClicked = formState.tasteChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.TasteCheck(!formState.tasteChk))
                                }
                            }
                        )
                        SingleIconWithText(
                            text = "위생",
                            UnClickedSourceId = R.drawable.ic_place_info_clean_unclicked,
                            ClickedSourceId = R.drawable.ic_place_info_clean_clicked,
                            isClicked = formState.cleanChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.CleanCheck(!formState.cleanChk))
                                }
                            }
                        )
                        SingleIconWithText(
                            text = "친절",
                            UnClickedSourceId = R.drawable.ic_place_info_kind_unclicked,
                            ClickedSourceId = R.drawable.ic_place_info_kind_clicked,
                            isClicked = formState.kindChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.KindCheck(!formState.kindChk))
                                }
                            }
                        )
                        SingleIconWithText(
                            text = "분위기",
                            UnClickedSourceId = R.drawable.ic_place_info_mood_unclicked,
                            ClickedSourceId = R.drawable.ic_place_info_mood_clicked,
                            isClicked = formState.vibeChk,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.VibeCheck(!formState.vibeChk))
                                }
                            }
                        )
                    }
                }
                ThickDivider()
                Column(
                    modifier = Modifier
                        .padding(vertical = 25.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Row {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    takePhotoFromCameraLauncher.launch(
                                        viewModel.getPictureIntent(context)
                                    )
                                }
//                                takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                            },
                            modifier = Modifier
                                .size(placeInfoMenuImageSize.dp)
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Unspecified
                            )
                        }
                        Spacer(Modifier.width(6.dp))
                        LazyRow {
                            items(formState.placeImages) { image ->
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier
                                        .width(placeInfoMenuImageSize.dp)
                                        .height(placeInfoMenuImageSize.dp)
                                ) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = image
                                        ),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(5.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                                Spacer(Modifier.width(6.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    PlaceReviewInputText(
                        text = formState.review,
                        placeholder = "맛집 리뷰",
                        lengthText = formState.reviewTextLength,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.ReviewChange(it))
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Row() {
                        TitleText("메뉴평가")
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                //isClicked.value = !isClicked.value
                            },
                            modifier = Modifier
                                .size(17.dp)
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.ic_add_menu_eval_button),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
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
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    InputTextField(
                        text = formState.menuName,
                        placeholder = "메뉴명",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuNameChange(it))
                            }
                        }
                    )
                    InputTextField(
                        text = formState.menuPrice,
                        placeholder = "가격",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuPriceChange(it))
                            }
                        }
                    )
                    InputTextField(
                        text = formState.menuMemo,
                        placeholder = "메모",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuMemoChange(it))
                            }
                        }
                    )
                    Spacer(Modifier.height(70.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    MainButton(
                        text = "맛집 등록하기",
                        onClick = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                            }
                        }
                    )
                    Spacer(Modifier.height(buttonBottomValue.dp))
                }
            }
        }
    }
}