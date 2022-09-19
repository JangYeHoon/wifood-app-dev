package com.example.wifood.presentation.util

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.wifood.R

sealed class NavItem(var title: String, var icon: Int, var id: String) {
    object Map : NavItem("지도", R.drawable.ic_bottom_map_icon, "map")
    object List : NavItem("리스트", R.drawable.ic_bottom_list_icon, "list")
    object MyPage : NavItem("설정", R.drawable.ic_bottom_settings_icon, "mypage")
}
