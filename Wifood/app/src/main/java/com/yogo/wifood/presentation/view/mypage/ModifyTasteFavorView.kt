package com.yogo.wifood.presentation.view.mypage

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.mypage.contents.ModifyTasteFavorContent
import com.yogo.wifood.presentation.view.mypage.MyPageViewModel
@Composable
fun ModifyTasteFavorView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    ModifyTasteFavorContent(
        favorSelected = { select, index ->
            viewModel.onEvent(
                MyPageEvent.FavorSelected(
                    select + 1,
                    index
                )
            )
        },
        onClickChangeTasteButton = {
            viewModel.onEvent(MyPageEvent.ModifyUserInfo("TASTE"))
            navController.navigateUp()
        },
        onCucumberClicked = {
            SignUpData.cucumberClicked = it
        },
        onCorianderClicked = {
            SignUpData.corianderClicked = it
        },
        onMintClicked = {
            SignUpData.mintChokoClicked = it
        },
        onEggplantClicked = {
            SignUpData.eggplantClicked = it
        }
    )
}