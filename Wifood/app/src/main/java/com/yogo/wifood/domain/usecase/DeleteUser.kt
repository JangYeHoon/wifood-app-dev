package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String) {
        repository.deleteUser(id)
    }
}