package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.Place

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
    var imageNameList: ArrayList<String> = arrayListOf(),
    var review: String = "",
    var latitude: Float = 0f,
    var longitude: Float = 0f,
    var address: String = "",
    val menuGradeDtoList: ArrayList<MenuGradeDto> = arrayListOf()
) {
//    fun toPlace(): Place {
//        return Place(
//            placeId = placeId,
//            groupId = groupId,
//            name = name,
//            menu = menu,
//            visited = visited,
//            score = score,
//            tasteChk = tasteChk,
//            cleanChk = cleanChk,
//            kindChk = kindChk,
//            imageNameList = imageNameList,
//            review = review,
//            latitude = latitude,
//            longitude = longitude,
//            address = address,
//            menuGradeList = menuGradeDtoList.map { it.toMenuGrade() }
//        )
//    }
}
