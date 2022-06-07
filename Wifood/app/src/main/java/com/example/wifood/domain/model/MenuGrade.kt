package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuGrade(
    val placeId: Int,
    var name: String,
    var price: Int,
    var memo: String
) : Parcelable
