package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Taste(
    val userId: String,
    @PrimaryKey val tasteId: Int,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int
)
