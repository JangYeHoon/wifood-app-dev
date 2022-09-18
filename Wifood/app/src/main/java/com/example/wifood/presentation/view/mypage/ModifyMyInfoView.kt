package com.example.wifood.presentation.view.mypage

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.mypage.contents.ModifyMyInfoContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyMyInfoView(
    navController: NavController
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
            // TODO(회원탈퇴 눌렀을 때)
        },
    )
}