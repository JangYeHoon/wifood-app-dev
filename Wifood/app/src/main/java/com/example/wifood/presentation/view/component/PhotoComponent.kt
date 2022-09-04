package com.example.wifood.presentation.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.wifood.R


@Composable
fun PhotoDefaultIcon(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .size(60.dp)
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
            contentDescription = "",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )
    }
}


@Composable
fun PhotoListUp(
    imageList: List<Int> = listOf(R.drawable.place_image, R.drawable.place_image)
){
    val photoSize = 60
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        PhotoDefaultIcon(
            modifier = Modifier.size(photoSize.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))

        LazyRow {
            items(imageList) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "",
                    modifier = Modifier
                        .size(photoSize.dp)
                        .clip(
                            RoundedCornerShape(5.dp)
                        ),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
