package com.example.wifood.presentation.view.placeList

import android.net.Uri
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.google.maps.android.compose.MapProperties

data class PlaceInfoState(
    val place: Place? = null,
    val group: Group? = null,
    val placeImageUris: List<Uri> = emptyList(),
    val popupImageIdx: Int = -1,
    val placeReview: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
