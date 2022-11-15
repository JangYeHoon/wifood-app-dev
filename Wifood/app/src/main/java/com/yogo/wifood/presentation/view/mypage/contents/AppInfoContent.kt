package com.yogo.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.presentation.view.mypage.component.CommonTextButton
import com.yogo.wifood.presentation.view.mypage.component.versionInfoField

@Composable
fun AppInfoContent(
    onBackButtonClicked: () -> Unit = {},
    onVersionInfoClicked:() -> Unit = {},
    onDeveloperInfoClicked:() -> Unit = {},
    onServiceAgreementClicked:() -> Unit = {},

) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "앱정보",
            leftButtonOn = true,
            leftButtonClicked = onBackButtonClicked
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            versionInfoField(
                text = "버젼 정보",
                version = "1.0v",
                onClick = onVersionInfoClicked
            )
            CommonTextButton(
                text = "개발자 정보",
                withButton = true,
                onClick = onDeveloperInfoClicked
            )
            CommonTextButton(
                text = "서비스 이용약관",
                withButton = true,
                onClick = onServiceAgreementClicked
            )
        }
    }
}