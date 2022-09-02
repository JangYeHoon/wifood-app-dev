package com.example.wifood.presentation.view.placeList.placeinfowrite

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.placeList.component.CameraAndAlbumBottomSheetContent
import com.example.wifood.presentation.view.placeList.component.PlaceReviewInputText
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputReviewAndImages(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val placeInfoMenuImageSize = 60

    val formState = viewModel.formState
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    BackHandler(enabled = true) {
        navController.previousBackStackEntry?.savedStateHandle?.set("placeBack", state.place)
        navController.popBackStack()
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

    ModalBottomSheetLayout(
        sheetContent = { CameraAndAlbumBottomSheetContent() },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            topBar = {
                YOGOTopAppBar(
                    text = "맛집 등록",
                    leftButtonClicked = {/*TODO*/ },
                    rightButtonOn = true,
                    rightButtonClicked = {
                        scope.launch {
                            viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 25.dp)
                        .padding(horizontal = 24.dp)
                ) {
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
                    Spacer(Modifier.height(10.dp))
                    Row {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    modalBottomSheetState.show()
                                }
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
                }

                Column(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 32.dp)
                ) {
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