package com.yogo.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.model.User
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): LiveData<User> {
        val user = MutableLiveData<User>()
        repository.getUserInfo(id).observeForever {
            user.value = it
        }
        return user
    }
}