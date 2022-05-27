package com.example.wifood.domain.usecase

import android.util.Log
import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAll @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(userId: String): Flow<Resource<User>> = flow {
        try {
            Log.d("TEST", "GetAll launched")
            val user = userId.replace('.', '_')
            repository.getAll(user)
        } catch (e: Exception) {
            Log.d("TEST", "GetAll error occured")
        }
    }
}