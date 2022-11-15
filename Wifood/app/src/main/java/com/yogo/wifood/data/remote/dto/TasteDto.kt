package com.yogo.wifood.data.remote.dto

import com.yogo.wifood.domain.model.Taste

data class TasteDto(
    val userId: String = "",
    val spicy: Int = -1,
    val salty: Int = -1,
    val acidity: Int = -1,
    val sour: Int = -1,
    val sweet: Int = -1,
    val cucumber: Boolean = false,
    val coriander: Boolean = false,
    val mintChoco: Boolean = false,
    val eggplant: Boolean = false
) {
    fun toTaste(): Taste {
        return Taste(
            userId = userId,
            spicy = spicy,
            salty = salty,
            acidity = acidity,
            sour = sour,
            sweet = sweet,
            cucumber, coriander, mintChoco, eggplant
        )
    }
}
