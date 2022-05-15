package com.example.wifood.data.remote

import androidx.lifecycle.LiveData
import com.example.wifood.domain.entity.Group
import com.example.wifood.domain.entity.Place

interface WifoodApi {

    fun getGroupList(): LiveData<MutableList<Group>>

    fun getPlaceList(): LiveData<MutableList<Place>>
}