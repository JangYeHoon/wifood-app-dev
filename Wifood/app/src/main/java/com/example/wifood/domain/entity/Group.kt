package com.example.wifood.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    var id: Int = 0,
    var name: String = "Name",
    var color: String = "#FF9800",
    var theme: String = "",
    var order: Int = 0,
    var red: Float = 255F,
    var green: Float = 255F,
    var blue: Float = 255F
) : Parcelable