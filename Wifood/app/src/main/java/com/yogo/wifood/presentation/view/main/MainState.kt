package com.yogo.wifood.presentation.view.main

import android.location.Location
import android.net.Uri
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.MenuGrade
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.User
import com.yogo.wifood.presentation.util.NavItem
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

data class MainState(
    val user: User? = null,
    val groups: List<Group> = emptyList(),
    val places: List<Place> = emptyList(),
    val menus: List<MenuGrade> = emptyList(),
    val selected: String = NavItem.Map.id,
    val selectedGroupSheet: Group? = null,
    val isLoading: Boolean = false,
    val properties: MapProperties = MapProperties(isMyLocationEnabled = true),
    val selectedGroupId: Int = 0,
    val currentLocation: Location? = null,
    val field: List<com.google.android.libraries.places.api.model.Place.Field> = listOf(
        com.google.android.libraries.places.api.model.Place.Field.NAME,
        com.google.android.libraries.places.api.model.Place.Field.LAT_LNG,
        com.google.android.libraries.places.api.model.Place.Field.ADDRESS
    ),
    val searchResultName: String = "",
    val searchResultLatLng: LatLng? = null,
    val searchPlaceCameraMoveChk: Boolean = true,
    val placeImages: MutableMap<Int, Uri> = mutableMapOf()
)
