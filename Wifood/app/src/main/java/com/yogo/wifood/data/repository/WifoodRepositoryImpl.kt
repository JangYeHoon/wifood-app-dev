package com.yogo.wifood.data.repository

import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.yogo.wifood.data.remote.WifoodApi
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.model.User
import com.yogo.wifood.domain.repository.WifoodRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.storage.UploadTask

class WifoodRepositoryImpl(
    private val api: com.yogo.wifood.data.remote.WifoodApi
) : WifoodRepository {
    override fun deleteUser(id: String) {
        api.deleteUser(id)
    }

    override fun checkUser(id: String): LiveData<Int> {
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

    override fun updatePlace(place: Place) {
        api.updatePlace(place)
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

    override fun deletePlaceImages(groupId: Int, placeId: Int) {
        api.deletePlaceImages(groupId, placeId)
    }

    override fun deleteGroupImages(groupId: Int) {
        api.deleteGroupImages(groupId)
    }

    override fun insertProfile(image: Uri, id: String): UploadTask {
        return api.insertProfile(image, id)
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

    override fun getProfile(id: String): LiveData<Uri> {
        return api.getProfile(id)
    }

    override fun getTMapReverseGeocoding(latLng: LatLng): LiveData<String> {
        return api.getTMapReverseGeocoding(latLng)
    }

    override suspend fun requestCertNumber(phoneNumber: String): String {
        Log.d("KTOR", "Repository In")
        return api.requestCertNumber(phoneNumber)
    }
}