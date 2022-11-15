package com.yogo.wifood.presentation.view.mypage

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogo.wifood.presentation.view.mypage.contents.ServiceUsingDocumentContent

@Composable
fun ServiceUsingDocumentView(
    navController: NavController
) {
    ServiceUsingDocumentContent(
        onBackButtonClicked = {
            navController.popBackStack()
        }
    )
}