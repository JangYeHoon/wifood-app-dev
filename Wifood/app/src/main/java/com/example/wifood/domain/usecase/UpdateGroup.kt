package com.example.wifood.domain.usecase

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class UpdateGroup @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(group: Group) {
        repository.updateGroup(group)
    }
}