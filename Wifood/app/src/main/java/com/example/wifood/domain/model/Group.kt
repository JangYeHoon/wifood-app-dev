package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val groupId: Int,
    val userId: String,
    val name: String,
    val description: String,
    val color: Int,
    val placeList: List<Place>
) : Parcelable