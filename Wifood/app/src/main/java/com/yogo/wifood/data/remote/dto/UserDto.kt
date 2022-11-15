package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.User

data class UserDto(
    val phoneNumber: String = "",
    val address: String = "",
    val birthday: String = "",
    val gender: Int = -1,
    val nickname: String = "",
    var groupList: List<GroupDto> = emptyList(),
    var taste: TasteDto? = null
) {
    fun toUser(): User {
        return User(
            phoneNumber = phoneNumber,
            address = address,
            birthday = birthday,
            gender = gender,
            nickname = nickname,
            groups = groupList.map { it.toGroup() },
            taste = taste?.toTaste()
        )
    }
}

