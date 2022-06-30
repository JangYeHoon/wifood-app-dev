package com.example.wifood.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

interface WifoodRepository {
    fun getGroups(): LiveData<MutableList<Group>>

    fun deleteGroup(groupId: Int)

    fun insertGroup(group: Group)

    fun updateGroup(group: Group)

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun deletePlace(groupId: Int, placeId: Int)

    fun insertPlace(place: Place)

    fun getUser(id: String): LiveData<User>

    fun checkNickname(nickname: String): Boolean

    fun insertUser(user: User)

    fun insertPlaceImages(groupId: Int, placeId: Int, images: ArrayList<Bitmap>)
}