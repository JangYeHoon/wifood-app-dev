package com.example.wifood.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.wifood.data.remote.dto.MenuGradeDto

@Entity(
    tableName = "places",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("groupId"),
            childColumns = arrayOf("parentGroupId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaceEntity(
    @PrimaryKey val placeId: Int,
    val parentGroupId: Int,
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
    var address: String
)
