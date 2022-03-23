package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search(
    val fullAddress: String = "", val name: String = "",
    val latitude: Double = 0.0, val longitude: Double = 0.0, val bizName: String = ""
) : Parcelable