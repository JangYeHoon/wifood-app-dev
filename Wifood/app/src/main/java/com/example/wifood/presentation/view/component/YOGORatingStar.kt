package com.example.wifood.presentation.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.R

@Composable
fun YOGORatingStar(
    selectedArray: MutableList<Int>,
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
    selectedArray: MutableList<Int>,
    selectedValue: Int = 0,
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
            ImageVector.vectorResource(id = if (selectedArray[selectedValue] == 1) R.drawable.ic_rating_star_selected else R.drawable.ic_rating_star_unselected),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    for (i: Int in 0..4) {
                        if (i <= selectedValue)
                            selectedArray[i] = 1
                        else
                            selectedArray[i] = 0
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