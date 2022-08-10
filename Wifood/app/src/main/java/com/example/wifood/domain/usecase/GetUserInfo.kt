package com.example.wifood.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
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