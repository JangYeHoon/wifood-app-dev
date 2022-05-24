package com.example.wifood.presentation.view.map

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val groupList: List<Group> = emptyList(),
    val placeList: List<Place> = emptyList(),
    val selected: Int = 0
)
