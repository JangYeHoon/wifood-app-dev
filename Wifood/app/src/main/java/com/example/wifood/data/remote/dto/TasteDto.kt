package com.example.wifood.data.remote.dto

import com.example.wifood.domain.model.Taste

data class TasteDto(
    val userId: String,
    val tasteId: Int,
    val spicy: Int,
    val salty: Int,
    val acidity: Int,
    val sour: Int,
    val sweet: Int
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
