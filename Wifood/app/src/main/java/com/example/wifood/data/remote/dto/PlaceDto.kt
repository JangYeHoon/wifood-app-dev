package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.Place

data class PlaceDto(
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
    var latitude: Float,
    var longitude: Float,
    var address: String,
    val menuGradeDtoList: List<MenuGradeDto>
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
