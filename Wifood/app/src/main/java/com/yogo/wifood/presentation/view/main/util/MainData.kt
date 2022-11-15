package com.yogo.wifood.presentation.view.main.util

import android.location.Location
import android.net.Uri
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.Taste
import com.yogo.wifood.domain.model.User

object MainData {
    var user: User = User(
        phoneNumber = "",
        address = "",
        nickname = "",
        taste = null,
        groups = emptyList(),
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
    var image: String = ""
    var location: Location? = null
}