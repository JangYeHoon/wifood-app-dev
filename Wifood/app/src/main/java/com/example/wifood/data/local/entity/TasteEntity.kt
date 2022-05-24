package com.example.wifood.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "taste",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("parentUserId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TasteEntity(
    val parentUserId: String,
    @PrimaryKey val tasteId: Int,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int
)
