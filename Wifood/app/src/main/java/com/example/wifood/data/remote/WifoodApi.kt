package com.example.wifood.data.remote

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.data.remote.dto.TMapSearch
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.storage.UploadTask

interface WifoodApi {

    fun getGroups(): LiveData<MutableList<Group>>

    fun deleteGroup(groupId: Int)

    fun insertGroup(group: Group)

    fun updateGroup(group: Group)

    fun getPlaceList(): LiveData<MutableList<Place>>

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun deletePlace(groupId: Int, placeId: Int)

    fun insertPlace(place: Place)

    fun getUserAllData(id: String): LiveData<User>

    fun getUserInfo(id: String): LiveData<User>

    fun checkNickname(nickname: String): Boolean

    fun insertUser(user: User)

    fun insertPlaceImages(groupId: Int, placeId: Int, images: ArrayList<Uri>): UploadTask

    fun getTMapSearchPlaceResult(
        keyword: String,
        currentLocation: Location
    ): LiveData<MutableList<TMapSearch>>
}