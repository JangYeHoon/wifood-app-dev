package com.example.wifood.domain.usecase

import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUser @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(user: User) = flow<Resource<Unit>> {
        try {
            emit(Resource.Loading())
            repository.insertUser(user)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}