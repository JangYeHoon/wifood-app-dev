package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userId: String,
    val pwd: String,
    val nickname: String,
    val phoneNumber: Int,
    val address: String,
    val birthday: String,
    val gender: Int
)