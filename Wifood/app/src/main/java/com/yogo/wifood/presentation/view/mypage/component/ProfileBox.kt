package com.yogo.wifood.presentation.view.groupComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.login.component.ExplainText
import com.yogo.wifood.presentation.view.login.component.TitleText

@Composable
fun ProfileBoxImageVector(
    userNickname: String = "닉네임",
    userId: String = "nickname@naver.com",
    userProfileImage: Int = R.drawable.ic_splash_icon
){
    val textColor: Color = Color.Black

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Image( // profile image
            ImageVector.vectorResource(id = R.drawable.ic_splash_icon),
            /*painter = rememberImagePainter(
                data = userProfileImage
            ),*/
            contentDescription = "",
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.width(14.dp))
        Column( // nickname and page
        ){
            TitleText(
                text = userNickname,
                color = textColor
            )
            Spacer(Modifier.height(5.dp))
            ExplainText(
                text = userId,
                color = textColor
            )
        }
    }
}

@Composable
fun ProfileBoxImageData(
    userNickname: String = "닉네임",
    userId: String = "nickname@naver.com",
    userProfileImage: Any
){
    val textColor: Color = Color.Black

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Image( // profile image
            painter = rememberImagePainter(
                data = userProfileImage
            ),
            contentDescription = "",
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.width(14.dp))
        Column( // nickname and page
        ){
            TitleText(
                text = userNickname,
                color = textColor
            )
            Spacer(Modifier.height(5.dp))
            ExplainText(
                text = userId,
                color = textColor
            )
        }
    }
}
