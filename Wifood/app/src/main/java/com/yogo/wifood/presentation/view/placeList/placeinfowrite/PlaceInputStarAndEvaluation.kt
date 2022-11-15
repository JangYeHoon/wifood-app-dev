package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.yogo.wifood.presentation.view.placeList.newPlaceListComposeView.RateFavor
import com.yogo.wifood.presentation.view.placeList.newPlaceListComposeView.SelectAdditionalFavor
import com.yogo.wifood.presentation.view.placeList.newPlaceListComposeView.SimpleText
import com.yogo.wifood.view.ui.theme.EnableColor
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
fun PlaceInputStarAndEvaluation(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val formState = viewModel.formState
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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
                    ImageVector.vectorResource(id = R.drawable.ic_2by4),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(6.dp))
                YOGOLargeText(
                    text = "맛집을 평가해주세요."
                )
                Spacer(Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = EnableColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 27.dp)
                        .padding(vertical = 42.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleText(
                        "맛의 만족도"
                    )
                    Spacer(Modifier.height(5.dp))
                    RateFavor(formState.starScore, viewModel)
                    Spacer(Modifier.height(36.dp))
                    SimpleText(
                        "추가적으로 뭐가 더 좋았는지"
                    )
                    Spacer(Modifier.height(20.dp))
                    SelectAdditionalFavor(
                        viewModel,
                        formState.tasteChk,
                        formState.cleanChk,
                        formState.kindChk,
                        formState.vibeChk
                    )
                }
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                DoubleButton(
                    leftButtonText = "건너뛰기",
                    leftButtonOn = true,
                    leftButtonClicked = {
                        val placeJson = Uri.encode(Gson().toJson(state.place))
                        navController.navigate("${Route.PlaceInputMenuEvaluation.route}/${placeJson}")
                    },
                    rightButtonText = "리뷰 입력하기",
                    rightButtonOn = true,
                    rightButtonClicked = {
                        val placeJson = Uri.encode(Gson().toJson(state.place))
                        navController.navigate("${Route.PlaceInputMenuEvaluation.route}/${placeJson}")
                    }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))

            }
        }
    }
}