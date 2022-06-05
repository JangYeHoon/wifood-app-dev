package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class DeletePlace @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int) {
        repository.deletePlace(groupId, placeId)
    }
}