package com.example.wifood.presentation.view.placeList.placeinfowrite

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.wifood.R
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.presentation.util.*
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.util.findActivity
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component_box.RatingWithText
import com.example.wifood.presentation.view.component_box.SingleIconWithText
import com.example.wifood.view.ui.theme.*
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputStarAndEvaluation(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val formState = viewModel.formState
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val sheetContent: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(sheetContent) }

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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
        sheetContent = { customSheetContent() },
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
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.PlaceInputReview.route}/${placeJson}")
                        }
                    )
                    Spacer(Modifier.height(buttonBottomValue.dp))
                }
            }
        }
    }
}