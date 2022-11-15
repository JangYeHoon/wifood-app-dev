package com.yogo.wifood.presentation.util

import com.yogo.wifood.R

sealed class NavItem(var title: String, var icon: Int, var id: String) {
    object Map : NavItem("지도", R.drawable.ic_bottom_map_icon, "map")
    object List : NavItem("리스트", R.drawable.ic_bottom_list_icon, "list")
    object MyPage : NavItem("설정", R.drawable.ic_bottom_settings_icon, "mypage")
}
