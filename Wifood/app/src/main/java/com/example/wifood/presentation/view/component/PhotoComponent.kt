package com.example.wifood.presentation.view.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R


@Composable
fun PhotoDefaultIcon(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .size(60.dp)
) {
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


@ExperimentalCoilApi
@Composable
fun PhotoListUpWithSelection(
    imageList: ArrayList<Uri>,
    photoAddClick: () -> Unit,
    imageSize: Int = 60
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PhotoDefaultIcon(
            modifier = Modifier.size(imageSize.dp),
            onClick = {
                photoAddClick()
            }
        )
        Spacer(modifier = Modifier.width(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            for (image in imageList) {
                Image(
                    painter = rememberImagePainter(
                        data = image
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(imageSize.dp)
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

@ExperimentalCoilApi
@Composable
fun ShowPhotoList(
    imageList: List<Uri>,
    imageSize: Int = 60
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        for (image in imageList) {
            Image(
                painter = rememberImagePainter(
                    data = image
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(imageSize.dp)
                    .clip(
                        RoundedCornerShape(5.dp)
                    ),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
//    showImagePopupChk.value = true
//    viewModel.onEvent(PlaceInfoEvent.ClickPlaceImage(idx))
//    if (showImagePopupChk.value)
//        PlaceImagePopup(state.placeImageUris, showImagePopupChk)
}
