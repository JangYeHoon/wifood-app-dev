package com.example.wifood.presentation.view.placeList.search

import android.location.Location
import com.example.wifood.data.remote.dto.TMapSearch

data class SearchPlaceFormState(
    val searchKeyword: String = "",
    val searchResults: List<TMapSearch> = emptyList(),
    val currentLocation: Location? = null
)