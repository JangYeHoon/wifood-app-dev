package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Taste(
    val userId: String,
    val tasteId: Int,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int
)
