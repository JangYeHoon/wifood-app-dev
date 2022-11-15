package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.view.component.PlaceInputTopAppBar
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.yogo.wifood.presentation.view.placeList.newPlaceInfo.YOGOSubTextField
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputMenuEvaluation(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val formState = viewModel.formState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val state = viewModel.state

    val placeBackStack =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Place>("placeBack")
            ?.observeAsState()
    placeBackStack?.value?.let {
        scope.launch {
            viewModel.onEvent(PlaceInfoWriteFormEvent.BackBtnClick(it))
        }
    }

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

    Scaffold(
        topBar = {
            PlaceInputTopAppBar(
                leftButtonClicked = {
                    navController.popBackStack()
                },
                rightButtonClicked = {
                    scope.launch {
                        viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
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
                    ImageVector.vectorResource(id = R.drawable.ic_3by4),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(6.dp))
                YOGOLargeText(
                    text = "맛집 정보를 등록해주세요."
                )
                Spacer(Modifier.height(24.dp))
                formState.menuGrades.forEachIndexed { index, menuGrade ->
                    YOGOSubTextField(
                        titleText = "메뉴명",
                        inputText = menuGrade.name,
                        placeholder = "메뉴명을 입력해주세요",
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuNameChange(index, it))
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
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuMemoChange(index, it))
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
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                DoubleButton(
                    leftButtonText = "건너뛰기",
                    leftButtonOn = viewModel.checkForm(),
                    leftButtonClicked = {
                        if (viewModel.checkForm()) {
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.PlaceInputReviewAndImages.route}/${placeJson}")
                        }
                    },
                    rightButtonText = "리뷰 입력하기",
                    rightButtonOn = viewModel.checkForm(),
                    rightButtonClicked = {
                        if (viewModel.checkForm()) {
                            val placeJson = Uri.encode(Gson().toJson(state.place))
                            navController.navigate("${Route.PlaceInputReviewAndImages.route}/${placeJson}")
                        }
                    }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}