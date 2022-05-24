package com.example.wifood.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndGroups(
    @Embedded val user: UserEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "parentUserId"
    )
    val groups: List<GroupEntity>
)
