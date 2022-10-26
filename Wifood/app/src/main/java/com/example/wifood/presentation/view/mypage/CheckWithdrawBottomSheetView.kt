package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun CheckWithdrawBottomSheetView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel(),
    modalBottomSheetState: ModalBottomSheetState
) {
    val scope = rememberCoroutineScope()

    CheckWithdrawBottomSheetContent(
        onLeftButtonClicked = {
            navController.popBackStack()
            viewModel.onEvent(MyPageEvent.DeleteUser)
        },
        onRightButtonClicked = {
            scope.launch {
                modalBottomSheetState.hide()
            }
        }
    )
}