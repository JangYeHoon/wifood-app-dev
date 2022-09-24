package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.presentation.view.mypage.component.versionInfoField

@Composable
fun AppInfoView(
    navController: NavController
) {
    AppInfoContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onDeveloperInfoClicked = {

        },
        onServiceAgreementClicked = {

        },
        onVersionInfoClicked = {
            navController.navigate(Route.Document.route)
        }
    )
}