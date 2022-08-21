package com.example.wifood.domain.repository

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.storage.UploadTask

interface WifoodRepository {
    fun getGroups(): LiveData<MutableList<Group>>

    fun deleteGroup(groupId: Int)

    fun insertGroup(group: Group)

    fun updateGroup(group: Group)

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun getPlaceImageUri(groupId: Int, placeId: Int): LiveData<Uri>

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

    fun getTMapSearchAddressResult(
        keyword: String
    ): LiveData<MutableList<TMapSearch>>

    suspend fun requestCertNumber(phoneNumber: String): String
}