package com.example.wifood.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wifood.data.remote.dto.UserDto
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
    val groupList: List<Group> = emptyList(),
    val taste: Taste? = null
) : Parcelable {
    fun toUserDto(): UserDto {
        return UserDto(
            userId = userId,
            pwd = pwd,
            nickname = nickname,
            phoneNumber = phoneNumber,
            address = address,
            birthday = birthday,
            gender = gender
        )
    }
}