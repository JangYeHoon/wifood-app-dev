package com.example.wifood.presentation.view.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.mypage.contents.CheckWithdrawBottomSheetContent
import com.example.wifood.presentation.view.mypage.contents.ModifyMyInfoContent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

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
            CheckWithdrawBottomSheetContent(
                modalBottomSheetState = modalBottomSheetState
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        ModifyMyInfoContent(
            navController,
            onBackButtonClicked = {
                navController.popBackStack()
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
}