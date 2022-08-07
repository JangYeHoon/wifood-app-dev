package com.example.wifood.domain.model

import android.os.Parcelable
import com.example.wifood.data.remote.dto.UserDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String,
    val pwd: String,
    val nickname: String,
    val phoneNumber: String,
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