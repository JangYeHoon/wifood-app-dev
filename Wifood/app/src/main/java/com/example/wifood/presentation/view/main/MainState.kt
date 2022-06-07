package com.example.wifood.presentation.view.main

import android.location.Location
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
import com.example.wifood.presentation.util.NavItem
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

data class MainState(
    val user: User? = null,
    val groups: List<Group> = emptyList(),
    val places: List<Place> = emptyList(),
    val menus: List<MenuGrade> = emptyList(),
    val selected: String = NavItem.Map.id,
    val isLoading: Boolean = false,
    val properties: MapProperties = MapProperties(),
    val selectedGroupId: Int = 0,
    val currentLocation: Location? = null
)
