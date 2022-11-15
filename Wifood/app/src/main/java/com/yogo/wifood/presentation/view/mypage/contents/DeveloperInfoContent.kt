package com.yogo.wifood.presentation.view.mypage.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.presentation.view.component.MyPageTopAppBar
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun DeveloperInfoContent(
    onBackButtonClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
    ) {
        MyPageTopAppBar(
            titleText = "개발자 정보",
            leftButtonOn = true,
            leftButtonClicked = onBackButtonClicked
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 30.dp)
        ) {
            Spacer(Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceEvenly
            ) {
                DeveloperComponent(
                    developerName = "JYH",
                    developerJob = "Team Leader",
                    backgroundColor = Color(0xFFFDF5F1),
                    circleColor = Color(0xFFE3783F),
                    imageWidth = 150,
                    imageHeight = 150,
                    resourceId = R.drawable.developer_3
                )
                DeveloperComponent(
                    developerName = "KKJ",
                    developerJob = "Developer",
                    backgroundColor = Color(0xFFFDFAF1),
                    circleColor = Color(0xFFF2BE45),
                    imageWidth = 95,
                    imageHeight = 127,
                    resourceId = R.drawable.developer_2
                )
            }
            Spacer(Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceEvenly
            ) {
                DeveloperComponent(
                    developerName = "KMH",
                    developerJob = "Developer",
                    backgroundColor = Color(0xFFFBFDF1),
                    circleColor = Color(0xFF9ABC76),
                    imageWidth = 105,
                    imageHeight = 117,
                    resourceId = R.drawable.developer_1
                )
                DeveloperComponent(
                    developerName = "JHY",
                    developerJob = "Designer",
                    backgroundColor = Color(0xFFF1F7FD),
                    circleColor = Color(0xFF6A829B),
                    imageWidth = 145,
                    imageHeight = 150,
                    resourceId = R.drawable.developer_4
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperComponent(
    resourceId: Int = R.drawable.ic_splash_image_1,
    developerName: String = "김강직",
    developerJob: String = "디자이너",
    backgroundColor: Color = Color(0xFFFDF5F1),
    circleColor: Color = Color(0xFFE3783F),
    imageWidth: Int = 140,
    imageHeight: Int = 150,
) {
    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .width(138.dp)
            .height(220.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
                    .size(117.dp)
            ) {
            }
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "",
                modifier = Modifier
                    .width(imageWidth.dp)
                    .height(imageHeight.dp)
            )
        }
        Spacer(Modifier.height(15.dp))
        Text(
            text = developerJob,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = circleColor,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = developerName,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Gray01Color,
        )
        Spacer(Modifier.height(15.dp))
    }

}