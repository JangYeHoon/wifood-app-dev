package com.example.wifood.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "menus",
    foreignKeys = [
        ForeignKey(
            entity = PlaceEntity::class,
            parentColumns = arrayOf("placeId"),
            childColumns = arrayOf("parentPlaceId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MenuGradeEntity(
    @PrimaryKey val menuId: Int,
    val parentPlaceId: Int,
    var name: String,
    var price: Int,
    var memo: String
)
