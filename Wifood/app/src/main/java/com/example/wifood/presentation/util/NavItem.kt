package com.example.wifood.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(var title: String, var icon: ImageVector, var id: String) {
    object Map : NavItem("지도", Icons.Filled.Map, "map")
    object List : NavItem("리스트", Icons.Filled.FavoriteBorder, "list")
    object MyPage : NavItem("마이페이지", Icons.Filled.PersonOutline, "mypage")
}
