package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class CheckUser @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): Boolean {
        return repository.checkUser(id)
    }
}