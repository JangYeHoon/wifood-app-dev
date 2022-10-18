package com.example.wifood.presentation.view.placeList.group

import android.annotation.SuppressLint
import android.net.Uri
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
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.groupComponent.TextAndInputField
import com.example.wifood.presentation.util.ValidationEvent
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupNameInputView(
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
                }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "다음",
                activate = state.name.isNotEmpty(),
                onClick = {
                    val groupJson =
                        Uri.encode(Gson().toJson(GroupDto(name = state.name).toGroup()))
                    navController.navigate("${Route.GroupDescInput.route}/${groupJson}")
                }
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}