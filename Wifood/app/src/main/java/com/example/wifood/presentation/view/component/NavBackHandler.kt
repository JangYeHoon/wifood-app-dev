package com.example.wifood.presentation.view.component

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.wifood.presentation.view.placeList.PlaceInfoEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BackBottomSheetHideOrMoveView(
    modalBottomSheetState: ModalBottomSheetState,
    viewRoute: String,
    viewModel: PlaceInfoViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    BackHandler(enabled = true) {
        if (modalBottomSheetState.isVisible) {
            scope.launch { modalBottomSheetState.hide() }
        } else {
            viewModel.onEvent(PlaceInfoEvent.ViewChangeEvent)
            navController.popBackStack(
                viewRoute,
                false
            )
        }
    }
}