package com.example.wifood.presentation.view.map.util

import androidx.annotation.DrawableRes
import com.example.wifood.R

sealed class Colors(@DrawableRes val res: Int, val main: Long, val sub: Long, val num: Int) {
    object One : Colors(R.drawable.ic_map_marker1, 0xFFA06BBC, 0xFFFBDCFA, 1)
    object Two : Colors(R.drawable.ic_map_marker2, 0xFFFB7A34, 0xFFFFF1CE, 2)
    object Three : Colors(R.drawable.ic_map_marker3, 0xFF59C3B5, 0xFFE0ECEA, 3)
    object Four : Colors(R.drawable.ic_map_marker4, 0xFF7EB2FF, 0xFFEBF8FF, 4)
    object Five : Colors(R.drawable.ic_map_marker5, 0xFFA69D17, 0xFFFCF8BF, 5)
    object Six : Colors(R.drawable.ic_map_marker6, 0xFF1B4CF9, 0xFFE2E5FF, 6)
    object Seven : Colors(R.drawable.ic_map_marker7, 0xFF6153A7, 0xFFDCD6FC, 7)
    object Eight : Colors(R.drawable.ic_map_marker8, 0xFFFE6080, 0xFFFFDFE4, 8)
    object Nine : Colors(R.drawable.ic_map_marker9, 0xFF7ACB3A, 0xFFEFFFE2, 9)
    object Ten : Colors(R.drawable.ic_map_marker10, 0xFF57BF7C, 0xFFE3F9E8, 0)
    object Default : Colors(R.drawable.ic_map_marker10, 0xFF57BF7C, 0xFFE3F9E8, -1)
}
