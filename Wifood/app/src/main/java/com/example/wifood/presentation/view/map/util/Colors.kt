package com.example.wifood.presentation.view.map.util

import androidx.annotation.DrawableRes
import com.example.wifood.R

sealed class Colors(@DrawableRes val id: Int) {
    object One : Colors(R.drawable.ic_map_marker1)
    object Two : Colors(R.drawable.ic_map_marker2)
    object Three : Colors(R.drawable.ic_map_marker3)
    object Four : Colors(R.drawable.ic_map_marker4)
    object Five : Colors(R.drawable.ic_map_marker5)
    object Six : Colors(R.drawable.ic_map_marker6)
    object Seven : Colors(R.drawable.ic_map_marker7)
    object Eight : Colors(R.drawable.ic_map_marker8)
    object Nine : Colors(R.drawable.ic_map_marker9)
    object Ten : Colors(R.drawable.ic_map_marker10)
}
