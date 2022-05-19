package com.example.wifood.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    var groupId: Int,
    var userId: Int,
    var name: String,
    var description: String,
    var color: Int,
    val placeList: ArrayList<Place>
) : Parcelable