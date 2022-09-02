package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Taste(
    val userId: String,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int,
    val cucumber: Boolean,
    val coriander: Boolean,
    val mintChoco: Boolean,
    val eggplant: Boolean
) : Parcelable
