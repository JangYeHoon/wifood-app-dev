package com.example.wifood.presentation.view.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.splash.component.PageUI
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingView(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = 3)
    val onboardPages = listOf(
        Page(
            "맛집 기록 어플, ",
            "요고",
            "\n맛집을 손 쉽게 찾아보세요",
            232,
            314,
            R.drawable.ic_walk_through_screen1
        ),
        Page(
            "나만의 맛집을 ",
            "기록",
            "하고\n한 줄 설명",
            222,
            287,
            R.drawable.ic_walk_through_screen2
        ),
        Page(
            "내 취향의 맛집을 ",
            "추천",
            "받고\n먹어보세요",
            253,
            274,
            R.drawable.ic_walk_through_screen3,
        )
    )

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            PageUI(page = onboardPages[page])
        }
        Spacer(Modifier.weight(1f))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = MainColor,
            inactiveColor = EnableColor,
            spacing = 10.dp
        )
        Spacer(Modifier.height(56.dp))

        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            MainButton(
                text = "시작하기",
                onClick = {
                    navController.navigate(Route.Login.route)
                }
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}
