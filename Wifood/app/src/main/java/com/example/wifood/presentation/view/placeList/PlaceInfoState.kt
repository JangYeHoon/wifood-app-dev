package com.example.wifood.presentation.view.placeList

import android.net.Uri
import com.example.wifood.domain.model.Place

data class PlaceInfoState(
    val place: Place? = null,
    val groupName: String = "",
    val placeImageUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
