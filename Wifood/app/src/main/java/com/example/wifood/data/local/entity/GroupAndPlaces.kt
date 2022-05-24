package com.example.wifood.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wifood.domain.model.Place

data class GroupAndPlaces(
    @Embedded val group: GroupEntity,

    @Relation(
        parentColumn = "groupId",
        entityColumn = "parentGroupId"
    )
    val places: List<PlaceEntity>
)
