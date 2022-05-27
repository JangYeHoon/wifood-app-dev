package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.Taste

data class TasteDto(
    val userId: String = "",
    val tasteId: Int = -1,
    val spicy: Int = -1,
    val salty: Int = -1,
    val acidity: Int = -1,
    val sour: Int = -1,
    val sweet: Int = -1
) {
    fun toTaste(): Taste {
        return Taste(
            userId = userId,
            tasteId = tasteId,
            spicy = spicy,
            salty = salty,
            acidity = acidity,
            sour = sour,
            sweet = sweet
        )
    }
}
