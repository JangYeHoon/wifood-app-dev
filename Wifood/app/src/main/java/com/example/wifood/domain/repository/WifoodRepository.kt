package com.example.wifood.domain.repository

interface WifoodRepository {

    suspend fun getAll(userId: String)
}