package com.yogo.wifood.presentation.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteViewModel
import kotlinx.coroutines.launch

@Composable
fun YOGORatingStar(
    selectedArray: List<Int>,
    spaceBetweenStar: Int = 10
) {
    Row(
    ) {
        RatingStarIcon(selectedArray, 0)
        Spacer(Modifier.width(spaceBetweenStar.dp))
        RatingStarIcon(selectedArray, 1)
        Spacer(Modifier.width(spaceBetweenStar.dp))
        RatingStarIcon(selectedArray, 2)
        Spacer(Modifier.width(spaceBetweenStar.dp))
        RatingStarIcon(selectedArray, 3)
        Spacer(Modifier.width(spaceBetweenStar.dp))
        RatingStarIcon(selectedArray, 4)
    }

}

@Composable
fun RatingStarIcon(
    selectedArray: List<Int>,
    selectedValue: Int = 0,
    starSize: Int = 27,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val scope = rememberCoroutineScope()

    IconButton(
        onClick = {},
        modifier = Modifier
            .size(starSize.dp)
    ) {
        Icon(
            ImageVector.vectorResource(id = if (selectedArray[selectedValue] == 1) R.drawable.ic_rating_star_selected else R.drawable.ic_rating_star_unselected),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    scope.launch {
                        viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(selectedValue))
                    }
                },
            tint = Color.Unspecified
        )
    }
}

@Composable
fun RatingStarIcon(
    isClicked: MutableState<Boolean>,
    starSize: Int = 27
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    IconButton(
        onClick = {},
        modifier = Modifier
            .size(starSize.dp)
    ) {
        Icon(
            ImageVector.vectorResource(id = if (isClicked.value) R.drawable.ic_rating_star_selected else R.drawable.ic_rating_star_unselected),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    isClicked.value = !isClicked.value
                },
            tint = Color.Unspecified
        )
    }
}
@Composable
fun SingleRatingStar(
    isClicked: Boolean,
    onClick: () -> Unit = {},
    starSize: Int = 27
) {

    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .size(starSize.dp)
    ) {
        Icon(
            ImageVector.vectorResource(id = if (isClicked) R.drawable.ic_rating_star_selected else R.drawable.ic_rating_star_unselected),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            tint = Color.Unspecified
        )
    }
}