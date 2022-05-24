package com.example.wifood.data.repository

import com.example.wifood.data.local.dao.WifoodDao
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.domain.repository.WifoodRepository

class WifoodRepositoryImpl(
    private val dao: WifoodDao,
    private val api: WifoodApi
) : WifoodRepository {
}