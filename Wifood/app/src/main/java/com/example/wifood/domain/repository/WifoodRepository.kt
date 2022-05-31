package com.example.wifood.domain.repository

import androidx.lifecycle.LiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.User

interface WifoodRepository {
    fun getGroupList(user: User): LiveData<MutableList<Group>>

    fun getUser(id: String): LiveData<User>
}