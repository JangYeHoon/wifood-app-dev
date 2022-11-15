package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.Group

data class GroupDto(
    var groupId: Int = -1,
    var userId: String = "",
    var name: String = "",
    var description: String = "",
    var color: Int = -1,
    var placeList: List<PlaceDto> = emptyList()
) {
    fun toGroup(): Group {
        return Group(
            groupId = groupId,
            userId = userId,
            name = name,
            description = description,
            color = color,
            places = placeList.map { it.toPlace() }
        )
    }
}