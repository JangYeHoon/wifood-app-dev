package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
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
    var imageNameList: List<String>,
    var review: String,
    var latitude: Double,
    var longitude: Double,
    var address: String,
    val menuList: List<MenuGrade>
) : Parcelable
