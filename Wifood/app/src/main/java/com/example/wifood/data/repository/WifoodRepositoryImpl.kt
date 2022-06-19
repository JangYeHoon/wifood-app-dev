package com.example.wifood.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.wifood.data.local.dao.WifoodDao
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class WifoodRepositoryImpl(
    private val dao: WifoodDao,
    private val api: WifoodApi
) : WifoodRepository {
    override fun getGroupList(user: User): LiveData<MutableList<Group>> {
        return api.getGroupList(user)
    }

    override fun deleteGroup(groupId: Int) {
        TODO("Not yet implemented")
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

    override fun getUser(id: String): LiveData<User> {
        Timber.d("Repository Launched")
        return api.getUser(id)
    }

    override fun checkNickname(nickname: String): Boolean {
        return api.checkNickname(nickname)
    }

    override fun insertUser(user: User) {
        api.insertUser(user)
    }
}