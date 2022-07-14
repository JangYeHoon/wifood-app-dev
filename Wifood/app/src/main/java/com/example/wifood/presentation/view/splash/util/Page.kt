package com.example.wifood.presentation.view.splash

import androidx.annotation.DrawableRes

data class Page(
    val title: String,
    val edge: String,
    val description: String,
    @DrawableRes val image: Int
)
