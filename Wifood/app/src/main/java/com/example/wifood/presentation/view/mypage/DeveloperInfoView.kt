package com.example.wifood.presentation.view.mypage

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.presentation.view.mypage.contents.DeveloperInfoContent


@Composable
fun DeveloperInfoView(
    navController: NavController
){
    DeveloperInfoContent(
        onBackButtonClicked = {
            navController.popBackStack()
        }
    )
}