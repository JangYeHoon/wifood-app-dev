package com.yogo.wifood.presentation.view.map.util

import androidx.annotation.DrawableRes
import com.yogo.wifood.R

sealed class Colors(@DrawableRes val res: Int, val main: Long, val sub: Long, val num: Int) {
    object One : Colors(R.drawable.ic_map_marker1, 0xFFDF6446, 0xFFFBDCFA, 1)
    object Two : Colors(R.drawable.ic_map_marker2, 0xFFE3783F, 0xFFFFF1CE, 2)
    object Three : Colors(R.drawable.ic_map_marker3, 0xFFEB9A3F, 0xFFE0ECEA, 3)
    object Four : Colors(R.drawable.ic_map_marker4, 0xFFEABD55, 0xFFEBF8FF, 4)
    object Five : Colors(R.drawable.ic_map_marker5, 0xFF9ABC76, 0xFFFCF8BF, 5)
    object Six : Colors(R.drawable.ic_map_marker6, 0xFF60A78D, 0xFFE2E5FF, 6)
    object Seven : Colors(R.drawable.ic_map_marker7, 0xFF6A829B, 0xFFDCD6FC, 7)
    object Eight : Colors(R.drawable.ic_map_marker8, 0xFF8BB19C, 0xFFFFDFE4, 8)
    object Nine : Colors(R.drawable.ic_map_marker9, 0xFFF2AAA1, 0xFFEFFFE2, 9)
    object Ten : Colors(R.drawable.ic_map_marker10, 0xFFAD9780, 0xFFE3F9E8, 0)
    object Default : Colors(R.drawable.ic_map_marker1, 0xFFDF6446, 0xFFE3F9E8, -1)
}
