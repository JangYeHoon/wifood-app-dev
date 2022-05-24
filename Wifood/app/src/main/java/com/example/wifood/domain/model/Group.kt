package com.example.wifood.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey val groupId: Int,
    val userId: Int,
    val name: String,
    val description: String,
    val color: Int
)