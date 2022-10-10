package com.example.wifood.presentation.view.mypage

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.mypage.contents.ModifyTasteFavorContent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
@Composable
fun ModifyTasteFavorView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
){
    val favorSelected: (Int, Int) -> Unit = { select, index ->
        (viewModel as MyPageViewModel).onEvent(
            MyPageEvent.FavorSelected(
                select + 1,
                index
            )
        )
    }
    ModifyTasteFavorContent(
        favorSelected = favorSelected,
        onClickChangeTasteButton = {
            viewModel.onEvent(MyPageEvent.ModifyUserInfo("TASTE"))
            navController.navigateUp()
        }
    )
}