package com.yogo.wifood.presentation.view.placeList.group

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.YOGOTopAppBar
import com.yogo.wifood.presentation.view.groupComponent.TextAndInputField
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.groupComponent.SimpleInputView
import com.yogo.wifood.presentation.view.placeList.component.YOGOBaseTextField
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupEditView(
    navController: NavController,
    viewModel: GroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.formState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    val scrollState = rememberScrollState()
    Column {
        MyPageTopAppBar(
            titleText = "",
            leftButtonClicked = {
                navController.popBackStack()
            },
            showUnderLine = false
        )
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
                Text(
                    text = "맛집 그룹을 수정하세요",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(54.dp))
                Text(
                    text = "그룹명",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(8.dp))
                YOGOBaseTextField(
                    text = state.name,
                    onValueChange = {
                        scope.launch {
                            viewModel.onEvent(GroupFormEvent.NameChange(it))
                        }
                    },
                    selectFunction = {
                        scope.launch {
                            viewModel.onEvent(GroupFormEvent.ResetNameText)
                        }
                    },
                    placeholderText = "샤로수길 맛집 저장소"
                )
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "상세 설명",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black2Color
                )
                Spacer(Modifier.height(8.dp))
                YOGOBaseTextField(
                    text = state.description,
                    onValueChange = {
                        scope.launch {
                            viewModel.onEvent(GroupFormEvent.NameChange(it))
                        }
                    },
                    selectFunction = {
                        scope.launch {
                            viewModel.onEvent(GroupFormEvent.ResetDescriptionText)
                        }
                    },
                    placeholderText = "디저트, 맛집, 술집"
                )
                Spacer(Modifier.weight(1f))
                MainButton(
                    text = "맛집 수정하기",
                    onClick = {
                        scope.launch {
                            viewModel.onEvent(GroupFormEvent.EditBtnClick)
                        }
                    },
                    activate = state.description.isNotEmpty() && state.name.isNotEmpty()
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }

    /*
    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text = "맛집 그룹 등록",
                leftButtonClicked = { navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(30.dp))
            TextAndInputField(
                titleText = "그룹명",
                inputFieldPlaceHolder = "그룹명",
                inputFieldText = state.name,
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.NameChange(it))
                    }
                },
                onValueReset = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.ResetNameText)
                    }
                }
            )
            Spacer(Modifier.height(15.dp))
            TextAndInputField(
                titleText = "상세설명",
                inputFieldPlaceHolder = "상세설명",
                inputFieldText = state.description,
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.DescriptionChange(it))
                    }
                },
                onValueReset = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.ResetDescriptionText)
                    }
                }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "맛집 등록하기",
                activate = state.description.isNotEmpty() && state.name.isNotEmpty(),
                onClick = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.EditBtnClick)
                    }
                }
            )
            Spacer(Modifier.height(50.dp))
        }
    }*/
}