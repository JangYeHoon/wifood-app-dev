package com.yogo.wifood.presentation.view.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.mypage.contents.CheckWithdrawBottomSheetView
import com.yogo.wifood.presentation.view.mypage.contents.ModifyMyInfoContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyMyInfoView(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)



    ModalBottomSheetLayout(
        sheetContent = {
            CheckWithdrawBottomSheetView(
                navController,
                modalBottomSheetState = modalBottomSheetState
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        val viewModel: MyPageViewModel = hiltViewModel()

        LaunchedEffect(true) {
            viewModel.validationEvents.collectLatest { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        navController.navigate(Route.GetPhoneNumber.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }

        ModifyMyInfoContent(
            onBackButtonClicked = {
                navController.navigateUp()
            },
            onModifyPhoneNumberClicked = {
                navController.navigate(Route.ModifyPhoneNumber.route)
            },
            onModifyUserLocationClicked = {
                navController.navigate(Route.ModifyAddress.route)
            },
            onModifyUserFavorClicked = {
                navController.navigate(Route.ModifyFavor.route)
            },
            onWithdrawClicked = {
                scope.launch {
                    modalBottomSheetState.show()
                }
            },
        )
    }
}
