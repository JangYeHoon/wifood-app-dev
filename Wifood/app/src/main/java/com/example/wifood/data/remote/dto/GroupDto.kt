package com.example.wifood.data.remote.dto

import com.example.wifood.data.local.entity.GroupEntity
import com.example.wifood.domain.model.Group

data class GroupDto(
    var groupId: Int,
    var userId: Int,
    var name: String,
    var description: String,
    var color: Int,
    val placeList: List<PlaceDto>
) {
    fun toGroup(): Group {
        return Group(
            groupId = groupId,
            userId = userId,
            name = name,
            description = description,
            color = color
        )
    }
}