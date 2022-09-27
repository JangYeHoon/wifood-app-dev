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
import com.example.wifood.presentation.view.placeList.contents.ImagePopUpContent

@Composable
fun ImagePopUpView(
    images: List<Uri>,
    showImagePopupChk: MutableState<Boolean>,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    ImagePopUpContent(
        onBackButtonClicked = {
            showImagePopupChk.value = false
        },
        onRightButtonClicked = {
            viewModel.onEvent(PlaceInfoEvent.ClickPopupLeft)
        },
        onLeftButtonClicked = {
            viewModel.onEvent(PlaceInfoEvent.ClickPopupRight)
        },
        popUpImage = images[viewModel.state.popupImageIdx]
    )
}