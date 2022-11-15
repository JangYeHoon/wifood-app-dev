package com.yogo.wifood.presentation.view.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.NavItem
import com.yogo.wifood.presentation.view.MySettingView
import com.yogo.wifood.presentation.view.component.BottomSheetContent
import com.yogo.wifood.presentation.view.main.conponents.YOGOBottomBar
import com.yogo.wifood.presentation.view.map.MapView
import com.yogo.wifood.presentation.view.placeList.PlaceListComposeView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainView(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var exitWaitTime = 0L
    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(key1 = true) {
        viewModel.init()
        viewModel.toast.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            scope.launch { modalBottomSheetState.hide() }
        } else {
            if (System.currentTimeMillis() - exitWaitTime >= 1500) {
                exitWaitTime = System.currentTimeMillis()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("한번 더 누르면 종료됩니다.")
                }
            } else {
                activity?.finish()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                state.selectedGroupSheet,
                navController,
                viewModel,
                modalBottomSheetState
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                /*when (state.selected) {
                    NavItem.Map.id -> {
                        MapTopAppBar(navController)
                    }
                }*/
            },
            bottomBar = {
                YOGOBottomBar(
                    selected = state.selected,
                    pushMapClicked = {
                        viewModel.onEvent(
                            MainEvent.ItemClicked(
                                "map"
                            )
                        )
                    },
                    pushListClicked = {
                        viewModel.onEvent(
                            MainEvent.ItemClicked(
                                "list"
                            )
                        )
                    },
                    pushSettingClicked = {
                        viewModel.onEvent(
                            MainEvent.ItemClicked(
                                "mypage"
                            )
                        )
                    }
                )
            }
        ) {
            when (state.selected) {
                NavItem.Map.id -> {
                    MapView(navController, navBackStackEntry)
                }
                NavItem.List.id -> {
                    PlaceListComposeView(modalBottomSheetState, navController)
                }
                NavItem.MyPage.id -> {
                    MySettingView(navController)
                }
            }
        }
    }
}

