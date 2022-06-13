package com.example.wifood.presentation.view.placeList.group

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.component_box.TextAndInputField
import kotlinx.coroutines.launch

@Composable
fun GroupAddView(
    navController: NavController,
    viewModel: GroupViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val state = viewModel.formState
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text = "맛집 그룹 등록",
                onBackButtonClicked = {/*TODO*/ }
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
                }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "맛집 등록하기",
                onClick = {/*TODO*/ }
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}