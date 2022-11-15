package com.yogo.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.model.User
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetUserAllData @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): LiveData<User> {
        val userId = id.replace('.', '_')
        val user = MutableLiveData<User>()
        repository.getUserAllData(userId).observeForever {
            user.value = it
        }
        return user
    }
}
