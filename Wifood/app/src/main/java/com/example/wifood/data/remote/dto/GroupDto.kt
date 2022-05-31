package com.example.wifood.data.remote.dto

import com.example.wifood.data.local.entity.GroupEntity
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place

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
            placeList = placeList.map { it.toPlace() }
        )
    }
}