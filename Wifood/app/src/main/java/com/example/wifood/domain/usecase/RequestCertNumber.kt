package com.example.wifood.domain.usecase

import android.util.Log
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestCertNumber @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(phoneNumber: String): Flow<Resource<String>> = flow {
        try {
            Log.d("KTOR", "UseCase In")
            emit(Resource.Loading())
            val result = repository.requestCertNumber(phoneNumber)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}