package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    var id: Int = 0, var name: String = "Name", var color: String = "0.0.0",
    var theme: String = "", var order: Int = 0
) : Parcelable