package com.example.wifood.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wifood.domain.model.Taste

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val userId: String,
    val pwd: String,
    val nickname: String,
    val phoneNumber: Int,
    val address: String,
    val birthday: String,
    val gender: Int,
    @Embedded val taste: TasteEntity
)
