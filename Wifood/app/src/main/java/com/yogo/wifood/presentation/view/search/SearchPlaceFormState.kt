package com.yogo.wifood.presentation.view.search

import android.location.Location
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.google.maps.android.compose.MapProperties

data class SearchPlaceFormState(
    val searchKeyword: String = "",
    val searchResults: List<TMapSearch> = emptyList(),
    val addressSearchResults: List<TMapSearch> = emptyList(),
    val addPlaceName: String = "",
    val addPlaceAddressSearch: String = "",
    val addPlaceContentPageCount: Int = 1,
    val properties: MapProperties = MapProperties(isMyLocationEnabled = true),
    val currentLocation: Location? = null,
    val roadAddressGeocoding: String = "",
    val oldAddressGeocoding: String = "",
    val place: Place? = null,
    val clickedAddressIdx: Int = -1
)