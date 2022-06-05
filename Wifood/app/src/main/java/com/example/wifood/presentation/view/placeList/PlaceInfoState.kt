package com.example.wifood.presentation.view.placeList

import android.net.Uri
import com.example.wifood.domain.model.Place

data class PlaceInfoState(
    val placeInfo: Place? = null,
    val groupName: String = "",
    val imageUri: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
