package com.example.wifood.presentation.view.mypage.NewMypageComposeView

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.mypage.component.CommonTextButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Black2Color

@Preview(showBackground = true)
@Composable
fun MySettingView(

){
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "설정",
            leftButtonOn = false,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ){

                }
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                modifier = Modifier
                    .size(55.dp)
                    .clip(
                        shape = CircleShape
                    ),
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = "요고247",
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Black2Color
            )
            Spacer(Modifier.weight(1f))
            Icon(
                ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = "back button",
                modifier = Modifier
                    .wrapContentSize()
                    //.width(5.dp)
                    //.height(9.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Unspecified
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF4F4F4)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE7E7E7)
                )
        ){
            Spacer(Modifier.height(4.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            CommonTextButton(
                text = "내 정보 수정",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "앱 정보",
                withButton = true,
                onClick = {
                    //TODO
                }
            )
            CommonTextButton(
                text = "로그아웃",
                withButton = false,
                onClick = {
                    //TODO
                }
            )
        }
    }
}