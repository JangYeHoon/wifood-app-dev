package com.yogo.wifood.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TMapSearch(
    val fullAddress: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val bizName: String = "",
    val oldAddress: String = ""
) : Parcelable