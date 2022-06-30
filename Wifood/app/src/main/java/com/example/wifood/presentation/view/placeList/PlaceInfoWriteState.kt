package com.example.wifood.presentation.view.placeList

import com.example.wifood.domain.model.Place

data class PlaceInfoWriteState(
    val place: Place? = null,
    val groupId: Int = 0,
    val error: String = ""
)