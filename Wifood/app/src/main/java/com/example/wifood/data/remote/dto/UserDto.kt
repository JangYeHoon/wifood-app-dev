package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.User

data class UserDto(
    val userId: String = "",
    val pwd: String = "",
    val nickname: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val birthday: String = "",
    val gender: Int = -1,
    var groupList: List<GroupDto> = emptyList(),
    var taste: TasteDto? = null
) {
    fun toUser(): User {
        return User(
            userId = userId,
            pwd = pwd,
            nickname = nickname,
            phoneNumber = phoneNumber,
            address = address,
            birthday = birthday,
            gender = gender,
            groupList = groupList.map { it.toGroup() },
            taste = taste?.toTaste()
        )
    }
}

