package com.example.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.MainColor


@Composable
fun DeveloperInfoView(

) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "개발자 정보",
            leftButtonOn = true,
            leftButtonClicked = {

            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MainColor
                        ),
                    ) {
                        append("Project Manager\n")
                    }
                    Spacer(Modifier.height(3.dp))
                    append("장예훈")
                },
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Gray01Color
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MainColor
                        ),
                    ) {
                        append("App Designer\n")
                    }
                    Spacer(Modifier.height(3.dp))
                    append("정혜연")
                },
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Gray01Color
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MainColor
                        ),
                    ) {
                        append("Architecture Designer\n")
                    }
                    Spacer(Modifier.height(3.dp))
                    append("김민형")
                },
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Gray01Color
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MainColor
                        ),
                    ) {
                        append("App Planner\n")
                    }
                    Spacer(Modifier.height(3.dp))
                    append("김강직")
                },
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Gray01Color
            )
        }

    }
}