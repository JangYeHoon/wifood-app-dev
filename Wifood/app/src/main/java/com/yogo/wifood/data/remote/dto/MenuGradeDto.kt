package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.MenuGrade
import java.text.DecimalFormat

data class MenuGradeDto(
    val placeId: Int = -1,
    var name: String = "",
    var price: Int = 0,
    var memo: String = ""
) {
    fun toMenuGrade(): MenuGrade {
        return MenuGrade(
            placeId = placeId,
            name = name,
            price = price,
            memo = memo
        )
    }

    fun getPriceToCommaString(priceInt: Int): String {
        val numberCommaFormat = DecimalFormat("#,###")
        return numberCommaFormat.format(priceInt)
    }
}

