package com.example.wifood.presentation.view.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.splash.contents.SplashContent
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
fun SplashView(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = 3)
    val onboardPages = listOf(
        Page(
            title = "맛집 기록 어플, ",
            edge = "요고",
            description = "\n맛집을 손 쉽게 찾아보세요",
            imageWidth = 232,
            imageHeight = 314,
            image = R.drawable.ic_walk_through_screen1
        ),
        Page(
            title = "나만의 맛집을 ",
            edge = "기록",
            description = "하고\n한 줄 설명",
            imageWidth = 222,
            imageHeight = 287,
            image = R.drawable.ic_walk_through_screen2
        ),
        Page(
            title = "내 취향의 맛집을 ",
            edge = "추천",
            description = "받고\n먹어보세요",
            imageWidth = 253,
            imageHeight = 274,
            image = R.drawable.ic_walk_through_screen3,
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                SplashContent(
                    title = onboardPages[page].title,
                    edge = onboardPages[page].edge,
                    description = onboardPages[page].description,
                    imageWidth = onboardPages[page].imageWidth,
                    imageHeight = onboardPages[page].imageHeight,
                    image = onboardPages[page].image
                )
            }
            if (pagerState.currentPage != 2) {
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
            } else {
                AnimatedVisibility(visible = true) {
                    MainButton(
                        text = "시작하기",
                        onClick = {
                            navController.navigate(Route.GetPhoneNumber.route)
                        }
                    )
                }
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}
