package com.example.wifood.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.User

interface WifoodRepository {
    fun getGroupList(user: User): LiveData<MutableList<Group>>

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun deletePlace(groupId: Int, placeId: Int)

    fun getUser(id: String): LiveData<User>
}