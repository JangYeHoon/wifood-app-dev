package com.example.wifood.domain.usecase

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.model.User
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): LiveData<User> {
        Log.d("TEST", "GetUser launched")
        val userId = id.replace('.', '_')
        val user = MutableLiveData<User>()
        repository.getUser(userId).observeForever {
            user.value = it
        }
        return user
    }
}
