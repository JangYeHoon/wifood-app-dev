package com.example.wifood.presentation.view.placeList.search

import android.location.Location
import com.example.wifood.domain.model.TMapSearch

data class SearchPlaceFormState(
    val searchKeyword: String = "",
    val searchResults: List<TMapSearch> = emptyList(),
    val currentLocation: Location? = null
)