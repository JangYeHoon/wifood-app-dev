package com.example.wifood.domain.usecase

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class InsertGroup @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(group: Group) {
        repository.insertGroup(group)
    }
}