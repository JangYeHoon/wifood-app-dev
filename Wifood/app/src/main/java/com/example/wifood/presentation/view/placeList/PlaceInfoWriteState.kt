package com.example.wifood.presentation.view.placeList

import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place

data class PlaceInfoWriteState(
    val place: Place = PlaceDto().toPlace(),
    val group: Group? = null,
    val error: String = ""
)