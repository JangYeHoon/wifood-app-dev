package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.User

data class UserDto(
    val userId: String,
    val pwd: String,
    val nickname: String,
    val phoneNumber: Int,
    val address: String,
    val birthday: String,
    val gender: Int,
    val groupList: List<GroupDto>,
    val taste: TasteDto
) {
    fun toUser(): User {
        return User(
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

