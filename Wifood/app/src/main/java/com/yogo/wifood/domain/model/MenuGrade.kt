package com.yogo.wifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MenuGrade(
    val placeId: Int,
    var name: String,
    var price: Int,
    var memo: String
) : Parcelable
