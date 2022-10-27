package com.example.wifood.presentation.view.mypage.contents

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.R
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.MainColor

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
                    .fillMaxWidth()
            ) {
                DeveloperComponent()
                Spacer(Modifier.width(24.dp))
                DeveloperComponent()
            }
            Spacer(Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DeveloperComponent()
                Spacer(Modifier.width(24.dp))
                DeveloperComponent()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperComponent(
    resourceId: Int = R.drawable.ic_splash_image_1,
    developerName: String = "김강직",
    developerJob: String = "디자이너"
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFFDF5F1),
                shape = RoundedCornerShape(24.dp)
            )
            .width(138.dp)
            .height(193.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MainColor,
                    shape = CircleShape
                )
                .size(117.dp)
        ) {
        }
        Icon(
            ImageVector.vectorResource(resourceId),
            contentDescription = "Developers",
            modifier = Modifier
                .size(100.dp),
            tint = Color.Unspecified
        )
        Text(
            text = developerName,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xFF1A1A1A),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-15).dp)
        )
    }

}