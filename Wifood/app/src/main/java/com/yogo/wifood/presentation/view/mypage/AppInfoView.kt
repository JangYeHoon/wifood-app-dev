package com.yogo.wifood.presentation.view.mypage.contents

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route

@Composable
fun AppInfoView(
    navController: NavController
) {
    AppInfoContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onVersionInfoClicked = {
        },
        onDeveloperInfoClicked = {
            navController.navigate(Route.DeveloperInfo.route)
        },
        onServiceAgreementClicked = {
            navController.navigate(Route.ServiceUsingAgreement.route)
        }
    )
}