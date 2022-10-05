package com.example.wifood.domain.usecase

import com.example.wifood.domain.model.Place
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class InsertPlace @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(place: Place) {
        repository.insertPlace(place)
    }
}