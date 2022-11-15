package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.TMapSearch

data class TMapSearchDto(
    val fullAddress: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val bizName: String = ""
) {
    fun toTMapSearch(): TMapSearch {
        return TMapSearch(
            fullAddress = fullAddress,
            name = name,
            latitude = latitude,
            longitude = longitude,
            bizName = bizName
        )
    }
}
