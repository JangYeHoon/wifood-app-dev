package com.example.wifood.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.wifood.data.remote.dto.PlaceDto

@Entity(
    tableName = "groups",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("parentUserId"),
            onDelete = CASCADE
        )
    ]
)
data class GroupEntity(
    @PrimaryKey val groupid: Int,
    val parentUserId: String,
    val name: String,
    val description: String,
    val color: Int
)
