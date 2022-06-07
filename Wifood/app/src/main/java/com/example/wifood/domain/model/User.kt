package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String,
    val pwd: String,
    val nickname: String,
    val phoneNumber: Int,
    val address: String,
    val birthday: String,
    val gender: Int,
    val groupList: List<Group>,
    val taste: Taste
) : Parcelable