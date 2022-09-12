package com.example.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.wifood.R

@Preview(showBackground = true)
@Composable
fun PopUpView(

){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xE6222222)),
    ){
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopStart)
        ){
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize(),
                tint = Color.White
            )
        }
        Image(
            painter = rememberImagePainter(
                data = R.drawable.ic_place_info_photo_default
            ),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .clip(
                    RoundedCornerShape(0.dp)
                ).align(Alignment.Center),
            contentScale = ContentScale.FillBounds
        )
    }
}