package com.example.wifood.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(val route: String) {
    object Login : Route("loginview")
    object Map : Route("mapview")
    object PlaceList : Route("placelistview")
}