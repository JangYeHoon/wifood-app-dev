package com.example.wifood.data.remote

import androidx.lifecycle.LiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

interface WifoodApi {

    fun getGroupList(user: User): LiveData<MutableList<Group>>

    fun getPlaceList(): LiveData<MutableList<Place>>

    fun getUser(id: String): LiveData<User>
}