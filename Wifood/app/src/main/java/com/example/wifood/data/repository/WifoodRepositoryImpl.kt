package com.example.wifood.data.repository

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.wifood.data.local.dao.WifoodDao
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class WifoodRepositoryImpl(
    private val dao: WifoodDao,
    private val api: WifoodApi
) : WifoodRepository {
    override fun checkUser(id: String): Boolean {
        return api.checkUser(id)
    }

    override fun getGroups(): LiveData<MutableList<Group>> {
        return api.getGroups()
    }

    override fun deleteGroup(groupId: Int) {
        return api.deleteGroup(groupId)
    }

    override fun insertGroup(group: Group) {
        api.insertGroup(group)
    }

    override fun updateGroup(group: Group) {
        api.updateGroup(group)
    }

    override fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>> {
        return api.getPlaceImageUris(groupId, placeId)
    }

    override fun deletePlace(groupId: Int, placeId: Int) {
        api.deletePlace(groupId, placeId)
    }

    override fun insertPlace(place: Place) {
        api.insertPlace(place)
    }

    override fun getUserAllData(id: String): LiveData<User> {
        return api.getUserAllData(id)
    }

    override fun getUserInfo(id: String): LiveData<User> {
        return api.getUserInfo(id)
    }

    override fun checkNickname(nickname: String): Boolean {
        return api.checkNickname(nickname)
    }

    override fun insertUser(user: User) {
        api.insertUser(user)
    }

    override fun insertPlaceImages(
        groupId: Int,
        placeId: Int,
        images: ArrayList<Uri>
    ): UploadTask {
        return api.insertPlaceImages(groupId, placeId, images)
    }

    override fun getTMapSearchPlaceResult(
        keyword: String,
        currentLocation: Location
    ): LiveData<MutableList<TMapSearch>> {
        return api.getTMapSearchPlaceResult(keyword, currentLocation)
    }

    override fun getTMapSearchAddressResult(
        keyword: String
    ): LiveData<MutableList<TMapSearch>> {
        return api.getTMapSearchAddressResult(keyword)
    }

    override fun getTMapSearchDetailAddressResult(
        keyword: String
    ): LiveData<MutableList<TMapSearch>> {
        return api.getTMapSearchDetailAddressResult(keyword)
    }

    override fun getPlaceImageUri(groupId: Int, placeId: Int): LiveData<Uri> {
        return api.getPlaceImageUri(groupId, placeId)
    }

    override fun getTMapReverseGeocoding(latLng: LatLng): LiveData<String> {
        return api.getTMapReverseGeocoding(latLng)
    }

    override suspend fun requestCertNumber(phoneNumber: String): String {
        Log.d("KTOR", "Repository In")
        return api.requestCertNumber(phoneNumber)
    }
}