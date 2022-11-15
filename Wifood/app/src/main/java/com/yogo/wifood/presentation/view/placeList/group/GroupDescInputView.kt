package com.yogo.wifood.presentation.view.placeList.group

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.YOGOTopAppBar
import com.yogo.wifood.presentation.view.groupComponent.TextAndInputField
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.groupComponent.SimpleInputView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupDescInputView(
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

    Column {
        MyPageTopAppBar(
            titleText = "",
            leftButtonClicked = {
                navController.popBackStack()
            },
            showUnderLine = false
        )
        SimpleInputView(
            explainText = "그룹에 대한\n간단한 설명을 해주세요.",
            textFieldText = state.description,
            onTextFieldValueChanged = {
                scope.launch {
                    viewModel.onEvent(GroupFormEvent.DescriptionChange(it))
                }
            },
            onTextFieldValueReset = {
                scope.launch {
                    viewModel.onEvent(GroupFormEvent.ResetDescriptionText)
                }
            },
            placeholderText = "맛집 그룹 설명",
            buttonText = "맛집 그룹 등록하기",
            onButtonClick = {
                scope.launch {
                    viewModel.onEvent(GroupFormEvent.AddBtnClick)
                }
            },
            buttonActivate = state.description.isNotEmpty()
        )
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
                text = "맛집 그룹 등록하기",
                activate = state.description.isNotEmpty(),
                onClick = {
                    scope.launch {
                        viewModel.onEvent(GroupFormEvent.AddBtnClick)
                    }
                }
            )
            Spacer(Modifier.height(50.dp))
        }
    }
    */
}