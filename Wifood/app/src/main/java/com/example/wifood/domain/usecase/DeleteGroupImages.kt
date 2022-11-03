package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class DeleteGroupImages @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int) {
        return repository.deleteGroupImages(groupId)
    }
}