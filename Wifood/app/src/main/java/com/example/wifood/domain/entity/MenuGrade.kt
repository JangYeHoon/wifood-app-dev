package com.example.wifood.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuGrade(
    val name: String = "None", val price: Int = 0, val grade: Double = 0.0,
    val memo: String = ""
) : Parcelable
