package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Group(
    val groupId: Int,
    val userId: Int,
    val name: String,
    val description: String,
    val color: Int,
    val placeList: List<Place>
)