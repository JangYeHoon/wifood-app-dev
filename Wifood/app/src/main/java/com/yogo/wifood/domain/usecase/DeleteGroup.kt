package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class DeleteGroup @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int) {
        repository.deleteGroup(groupId)
    }
}