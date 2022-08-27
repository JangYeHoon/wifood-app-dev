package com.example.wifood.presentation.view.search

import android.location.Location
import com.example.wifood.domain.model.TMapSearch
import com.google.maps.android.compose.MapProperties

data class SearchPlaceFormState(
    val searchKeyword: String = "",
    val searchResults: List<TMapSearch> = emptyList(),
    val addressSearchResults: List<TMapSearch> = emptyList(),
    val addPlaceName: String = "",
    val addPlaceAddressSearch: String = "",
    val addPlaceContentPageCount: Int = 1,
    val properties: MapProperties = MapProperties(isMyLocationEnabled = true),
    val currentLocation: Location? = null
)