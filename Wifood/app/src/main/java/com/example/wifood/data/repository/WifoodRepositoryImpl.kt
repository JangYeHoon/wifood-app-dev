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

    override fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>> {
        return api.getPlaceImageUris(groupId, placeId)
    }

    override fun deletePlace(groupId: Int, placeId: Int) {
        api.deletePlace(groupId, placeId)
    }

    override fun getUser(id: String): LiveData<User> {
        Log.d("TEST", "Repository Launched")
        return api.getUser(id)
    }
}