package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.Place

data class PlaceDto(
    val placeId: Int = -1,
    val groupId: Int = -1,
    var name: String = "",
    var menu: String = "",
    var visited: Boolean = false,
    var score: Float = 0f,
    var tasteChk: Boolean = false,
    var cleanChk: Boolean = false,
    var kindChk: Boolean = false,
    var vibeChk: Boolean = false,
    var imageNameList: ArrayList<String> = arrayListOf(),
    var review: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var address: String = "",
    val menuList: List<MenuGradeDto> = arrayListOf(),
    val bizName: String = ""
) {
    fun toPlace(): Place {
        return Place(
            placeId = placeId,
            groupId = groupId,
            name = name,
            menu = menu,
            visited = visited,
            score = score,
            tasteChk = tasteChk,
            cleanChk = cleanChk,
            kindChk = kindChk,
            vibeChk = vibeChk,
            imageNameList = imageNameList,
            review = review,
            latitude = latitude,
            longitude = longitude,
            address = address,
            menuList = menuList.map { it.toMenuGrade() },
            bizName = bizName
        )
    }
}
