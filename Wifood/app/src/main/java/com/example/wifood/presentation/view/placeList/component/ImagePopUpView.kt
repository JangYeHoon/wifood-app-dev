package com.example.wifood.presentation.view.placeList.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.view.placeList.PlaceInfoEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoViewModel

@Composable
fun ImagePopUpView(
    images: List<Uri>,
    showImagePopupChk: MutableState<Boolean>,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = {
            showImagePopupChk.value = false
        },
        properties = PopupProperties()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xE6222222))
                .clickable { showImagePopupChk.value = false },
        ) {
            IconButton(
                onClick = { showImagePopupChk.value = false },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart)
            ) {
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
                    data = images[viewModel.state.popupImageIdx]
                ),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .clip(
                        RoundedCornerShape(0.dp)
                    )
                    .align(Alignment.Center)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { },
                contentScale = ContentScale.FillBounds
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(PlaceInfoEvent.ClickPopupLeft)
                    },
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.onEvent(PlaceInfoEvent.ClickPopupRight)
                    },
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}