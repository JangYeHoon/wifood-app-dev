package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class DeletePlaceImages @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int) {
        return repository.deletePlaceImages(groupId, placeId)
    }
}