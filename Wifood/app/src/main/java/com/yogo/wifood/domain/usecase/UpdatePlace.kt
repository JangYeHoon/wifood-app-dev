package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class UpdatePlace @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(place: Place) {
        repository.updatePlace(place)
    }
}