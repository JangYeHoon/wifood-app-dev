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
    viewModel: MyPageViewModel = hiltViewModel(),
    modalBottomSheetState: ModalBottomSheetState
) {
    val scope = rememberCoroutineScope()

    val shape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    Column(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = shape
            )
            .fillMaxWidth()
            .padding(horizontal = sidePaddingValue.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(58.dp))
        Text(
            text = "회원탈퇴를\n" +
                    "하시겠습니까?",
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Gray01Color,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(42.dp))
        DoubleButton(
            leftButtonOn = true,
            leftButtonText = "탈퇴하기",
            leftButtonClicked = {
                viewModel.onEvent(MyPageEvent.DeleteUser)
            },
            rightButtonOn = true,
            rightButtonText = "되돌아가기",
            rightButtonClicked = {
                scope.launch {
                    modalBottomSheetState.hide()
                }
            },
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}