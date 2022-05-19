package com.example.wifood.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val placeId: Int,
    val groupId: Int,
    var name: String,
    var menu: String,
    var visited: Boolean,
    var score: Float,
    var tasteChk: Boolean,
    var cleanChk: Boolean,
    var kindChk: Boolean,
    var imageNameList: ArrayList<String>,
    var review: String,
    var latitude: Float,
    var longitude: Float,
    var address: String,
    val menuGradeList: ArrayList<MenuGrade>
) : Parcelable
