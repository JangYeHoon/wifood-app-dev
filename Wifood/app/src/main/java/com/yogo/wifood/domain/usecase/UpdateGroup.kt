package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class UpdateGroup @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(group: Group) {
        repository.updateGroup(group)
    }
}