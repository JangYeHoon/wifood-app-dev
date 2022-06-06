package com.example.wifood.data.remote

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

interface WifoodApi {

    fun getGroupList(user: User): LiveData<MutableList<Group>>

    fun deleteGroup(groupId: Int)

    fun getPlaceList(): LiveData<MutableList<Place>>

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun deletePlace(groupId: Int, placeId: Int)

    fun getUser(id: String): LiveData<User>
}