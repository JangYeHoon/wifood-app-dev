package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MenuGrade(
    val placeId: Int,
    var name: String,
    var price: Int,
    var memo: String
)
