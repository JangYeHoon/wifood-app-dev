package com.yogo.wifood.domain.model

import android.os.Parcelable
/* Domain layer is lowest layer. It means domain layer is affected by nothing. */
//checkPoint
import com.yogo.wifood.data.remote.dto.UserDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val phoneNumber: String,
    val address: String,
    val birthday: String,
    val gender: Int,
    val nickname: String,
    var groups: List<Group> = emptyList(),
    val taste: Taste? = null
) : Parcelable {
    fun toUserDto(): UserDto {
        return UserDto(
            phoneNumber = phoneNumber,
            address = address,
            birthday = birthday,
            gender = gender
        )
    }
}