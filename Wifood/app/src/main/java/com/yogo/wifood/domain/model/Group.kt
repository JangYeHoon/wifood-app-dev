package com.yogo.wifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Group(
    val groupId: Int = 0,
    val userId: String = "",
    var name: String = "",
    var description: String = "",
    val color: Int = 0,
    val places: List<Place> = emptyList()
) : Parcelable