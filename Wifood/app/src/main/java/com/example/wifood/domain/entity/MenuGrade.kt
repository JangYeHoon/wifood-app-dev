package com.example.wifood.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuGrade(
    val placeId: Int,
    var name: String,
    var price: Int,
    var memo: String
) : Parcelable
