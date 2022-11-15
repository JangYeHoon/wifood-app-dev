package com.yogo.wifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Taste(
    val userId: String,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int,
    val cucumber: Boolean,
    val coriander: Boolean,
    val mintChoco: Boolean,
    val eggplant: Boolean
) : Parcelable
