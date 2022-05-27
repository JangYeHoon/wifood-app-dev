package com.example.wifood.data.remote

import androidx.lifecycle.LiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place

interface WifoodApi {

    fun getGroupList(): LiveData<MutableList<GroupDto>>

    fun getPlaceList(): LiveData<MutableList<Place>>

    suspend fun getAll(userId: String)
}