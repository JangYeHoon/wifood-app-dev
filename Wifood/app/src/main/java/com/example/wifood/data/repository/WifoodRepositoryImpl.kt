package com.example.wifood.data.repository

import android.util.Log
import com.example.wifood.data.local.dao.WifoodDao
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.domain.repository.WifoodRepository
import kotlinx.coroutines.coroutineScope

class WifoodRepositoryImpl(
    private val dao: WifoodDao,
    private val api: WifoodApi
) : WifoodRepository {
    override suspend fun getAll(userId: String) {
        Log.d("TEST", "Repository launched")
        api.getAll(userId)
    }
}