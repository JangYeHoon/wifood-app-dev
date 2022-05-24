package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.MenuGrade

data class MenuGradeDto(
    val placeId: Int,
    var name: String,
    var price: Int,
    var memo: String
) {
    fun toMenuGrade(): MenuGrade {
        return MenuGrade(
            placeId = placeId,
            name = name,
            price = price,
            memo = memo
        )
    }
}

