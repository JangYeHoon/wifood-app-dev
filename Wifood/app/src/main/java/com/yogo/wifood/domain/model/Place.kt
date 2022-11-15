package com.yogo.wifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Place(
    var placeId: Int,
    var groupId: Int,
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
    var menuList: List<MenuGrade>,
    var vibeChk: Boolean,
    var bizName: String
) : Parcelable {
    fun deleteMenuGradeFromEmptyName() {
        menuList = menuList.filter { menuGrade -> menuGrade.name.isNotEmpty() }
    }
}
