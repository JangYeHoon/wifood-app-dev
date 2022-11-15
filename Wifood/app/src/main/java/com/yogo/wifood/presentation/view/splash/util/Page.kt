package com.yogo.wifood.presentation.view.splash

import androidx.annotation.DrawableRes

data class Page(
    val title: String,
    val edge: String,
    val description: String,
    val imageWidth: Int,
    val imageHeight: Int,
    @DrawableRes val image: Int
)
