package com.example.wifood.presentation.view.main.util

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.Taste
import com.example.wifood.domain.model.User

object MainData {
    var user: User = User(
        phoneNumber = "",
        address = "",
        nickname = "",
        taste = null,
        groupList = emptyList(),
        birthday = "",
        gender = -1
    )
    val taste = Taste(
        user.phoneNumber,
        3,
        3,
        3,
        3,
        3,
        false, false, false, false
    )
    var groups: List<Group> = emptyList()
    var places: List<Place> = emptyList()
    var pre: String = ""
}