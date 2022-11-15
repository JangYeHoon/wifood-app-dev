package com.yogo.wifood.presentation.view.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yogo.wifood.R
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.splash.contents.SplashContent
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.MainColor
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
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
            description = "\n내 취향의 맛집들을 찾고",
            imageWidth = 232,
            imageHeight = 314,
            image = R.drawable.ic_splash_image_1
        ),
        Page(
            title = "나만의 ",
            edge = "맛집 지도",
            description = "로\n찾기 좋게 만들고",
            imageWidth = 222,
            imageHeight = 287,
            image = R.drawable.ic_splash_image_2
        ),
        Page(
            title = "나만의 기준으로 ",
            edge = "맛집 후기",
            description = "도\n꼼꼼하게 기록하면 완고",
            imageWidth = 253,
            imageHeight = 274,
            image = R.drawable.ic_splash_image_3,
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp)
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
                Spacer(Modifier.height((56.5).dp))
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
