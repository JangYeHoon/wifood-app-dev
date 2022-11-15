package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import com.yogo.wifood.data.remote.dto.PlaceDto
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place

data class PlaceInfoWriteState(
    val place: Place = PlaceDto().toPlace(),
    val group: Group? = null,
    val error: String = ""
)