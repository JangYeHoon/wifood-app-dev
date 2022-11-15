package com.yogo.wifood.domain.repository

import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.model.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.storage.UploadTask

interface WifoodRepository {
    fun deleteUser(id: String)

    fun checkUser(id: String): LiveData<Int>

    fun getGroups(): LiveData<MutableList<Group>>

    fun deleteGroup(groupId: Int)

    fun insertGroup(group: Group)

    fun updateGroup(group: Group)

    fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>>

    fun getPlaceImageUri(groupId: Int, placeId: Int): LiveData<Uri>

    fun getProfile(id: String): LiveData<Uri>

    fun deletePlace(groupId: Int, placeId: Int)

    fun insertPlace(place: Place)

    fun updatePlace(place: Place)

    fun getUserAllData(id: String): LiveData<User>

    fun getUserInfo(id: String): LiveData<User>

    fun checkNickname(nickname: String): Boolean

    fun insertUser(user: User)

    fun insertPlaceImages(groupId: Int, placeId: Int, images: ArrayList<Uri>): UploadTask

    fun deletePlaceImages(groupId: Int, placeId: Int)

    fun deleteGroupImages(groupId: Int)

    fun insertProfile(image: Uri, id: String): UploadTask

    fun getTMapSearchPlaceResult(
        keyword: String,
        currentLocation: Location
    ): LiveData<MutableList<TMapSearch>>

    fun getTMapSearchAddressResult(
        keyword: String
    ): LiveData<MutableList<TMapSearch>>

    fun getTMapSearchDetailAddressResult(
        keyword: String
    ): LiveData<MutableList<TMapSearch>>

    fun getTMapReverseGeocoding(latLng: LatLng): LiveData<String>

    suspend fun requestCertNumber(phoneNumber: String): String
}